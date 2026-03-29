# API Gateway with Spring Cloud Gateway

This project is a **minimal learning workspace** focused on one topic: **API gateways** using **Spring Cloud Gateway** (reactive, Spring Boot 3). There is no Zuul code here; the README explains **when to choose Gateway vs Zuul** and how each concept below maps to the code and configuration.

---

## 1. API gateway, routing, and request filtering

**What it is**

An **API gateway** is a single entry point in front of many services. Clients call the gateway; the gateway **routes** requests to the right upstream (URI) and can run **filters** on the way in and out.

**Routing in this project**

Routes are declared in `src/main/resources/application.yml` under `spring.cloud.gateway.routes`. Each route has:

- **`id`**: logical name.
- **`uri`**: where to send the request (here, `https://httpbin.org` as a public stand-in for a real service).
- **`predicates`**: when this route applies (e.g. `Path=/open/**`).
- **`filters`**: what to do to the request/response for that route only (e.g. `StripPrefix`).

Example: a request to `GET http://localhost:8080/open/get` matches the `open-echo` route. The **`StripPrefix=1`** filter removes the first path segment (`open`), so the upstream request becomes `GET https://httpbin.org/get`.

**Request filtering**

Filtering happens at two levels:

- **Global filters**: run for every route (see section 3). Implemented as `GlobalFilter` beans.
- **Route filters**: only for routes that list them in YAML, or via `GatewayFilterFactory` (see `ApiKeyGatewayFilterFactory` and the `secure` route).

Together, **routing** picks the target service; **filters** implement cross-cutting behavior on that path.

---

## 2. Spring Cloud Gateway vs Netflix Zuul

**Spring Cloud Gateway (used here)**

- Built on **Spring WebFlux** and **Reactor Netty** (non-blocking I/O).
- Actively maintained as part of the Spring Cloud release train (e.g. 2024.0.x with Spring Boot 3.4).
- Fits **Spring Boot 3** and **reactive** stacks; integrates with Config, Discovery, Circuit Breaker, etc.

**Netflix Zuul**

- **Zuul 1.x**: servlet-based, blocking; **maintenance mode**; not aligned with Spring Boot 3’s default stack.
- **Zuul 2**: Netty-based and non-blocking, but **not** the default choice in new Spring projects; fewer first-class Spring integrations than Gateway.

**Practical takeaway**

For **new Spring Boot 3** work, **Spring Cloud Gateway** is the usual choice. Zuul belongs in **legacy** discussions or teams already standardized on it.

---

## 3. Route filters: pre and post

**Order of execution**

For each request, the gateway builds a chain: **global pre filters → route filters → proxy call → route post logic → global post filters** (conceptually; exact ordering is controlled by `getOrder()` and filter types).

**Pre filters**

Run **before** the request is sent upstream. Typical uses: logging, correlation IDs, authentication checks, adding headers.

In code: `PreRequestLoggingFilter` implements `GlobalFilter` with a **high precedence** (`Ordered.HIGHEST_PRECEDENCE + 10`) and logs method and path before `chain.filter(exchange)`.

**Post filters**

Run **after** the downstream response is available (often using `chain.filter(exchange).then(...)`).

In code: `PostResponseProcessingFilter` adds headers such as `X-Gateway-Trace-Id` and `Cache-Control: no-store` on the response.

**Route-scoped vs global**

- **Global**: `PreRequestLoggingFilter`, `PostResponseProcessingFilter` — apply to all routes.
- **Route**: e.g. `RequestRateLimiter` and `ApiKey` in YAML — only for the routes where they are listed.

---

## 4. Rate limiting and cross-cutting concerns

**Rate limiting**

Protects upstreams from abuse by capping how many requests a **key** (e.g. client IP) can make per time window.

Here, the **`RequestRateLimiter`** filter uses **Redis** (via `spring-boot-starter-data-redis-reactive`) to store token-bucket state. Configuration is under the `rate-limited-demo` route:

- **`replenishRate`**: steady tokens per second.
- **`burstCapacity`**: short bursts above the steady rate.
- **`key-resolver`**: SpEL reference `#{@ipKeyResolver}` → bean `ipKeyResolver` in `RedisRateLimiterConfig` (one counter per IP).

**Requirements**

Redis must be reachable at `spring.data.redis.host` / `port` (defaults: `localhost:6379`). Start Redis with:

```bash
docker compose up -d
```

**Try it**

With the app on port 8080 and Redis running:

```bash
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/demo/get
```

Repeat quickly; after exceeding the limit you should see **HTTP 429** (Too Many Requests).

**Cross-cutting concerns**

Anything that should be **consistent across services** without duplicating code in each microservice: logging, tracing headers, security, rate limits, retries, etc. The gateway is a natural place for **rate limiting** and **API key** checks (`ApiKeyGatewayFilterFactory` on the `/secure/**` route).

---

## 5. Gateway security and performance tuning

**Security (this repo)**

- **`GatewaySecurityConfig`**: Spring Security **WebFlux** (`SecurityWebFilterChain`). The demo **permits all** exchanges so you can call routes without logging in; **actuator** endpoints needed for observability are explicitly permitted.
- **`ApiKeyGatewayFilterFactory`**: route-level check for header `X-API-Key` (configurable name and secret via `GATEWAY_DEMO_API_KEY`, default in YAML). Wrong or missing key → **401**.

Example:

```bash
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/secure/get
curl -s -o /dev/null -w "%{http_code}\n" -H "X-API-Key: demo-gateway-secret" http://localhost:8080/secure/get
```

**Production-oriented next steps** (not implemented here, but typical):

- **OAuth2 Resource Server** (JWT validation at the gateway).
- **Mutual TLS** between gateway and upstreams.
- **Trusting forwarded headers** only behind a known load balancer (`Forwarded` / `X-Forwarded-*`).

**Performance tuning (configuration)**

In `application.yml`, under `spring.cloud.gateway.httpclient`:

- **Pool**: `max-connections`, `max-idle-time`, `max-life-time` — tune for your concurrency and upstream latency.
- **Timeouts**: `connect-timeout`, `response-timeout` — fail fast under load or slow peers.

**Observability**

Actuator exposes `health`, `info`, `gateway`, `metrics`, and `prometheus` (Micrometer Prometheus registry). Use metrics to watch latency, errors, and GC — and to validate pool and timeout settings under load.

---

## Run locally

1. **Redis** (required for `/demo/**` rate limiting):

   ```bash
   docker compose up -d
   ```

2. **Application**:

   ```bash
   mvn spring-boot:run
   ```

3. **Smoke tests**

   - Open route: `GET http://localhost:8080/open/get` → **200**, JSON from httpbin.
   - Rate-limited: `GET http://localhost:8080/demo/get` → **200** then **429** if you exceed limits (with Redis up).
   - API key: `GET http://localhost:8080/secure/get` with header `X-API-Key: demo-gateway-secret` (or your `GATEWAY_DEMO_API_KEY`) → **200**.

Without Redis, `/actuator/health` may report **DOWN** because the Redis health contributor runs, but routes that do not need rate limiting (for example `/open/**`) can still work once the app has started.

---

## Project layout

| Location | Role |
|----------|------|
| `ApiGatewayApplication.java` | Spring Boot entry point |
| `application.yml` | Routes, Redis, HTTP client tuning, actuator |
| `config/RedisRateLimiterConfig.java` | `KeyResolver` bean for rate limiting |
| `config/GatewaySecurityConfig.java` | Reactive security chain |
| `filter/PreRequestLoggingFilter.java` | Global **pre** filter |
| `filter/PostResponseProcessingFilter.java` | Global **post** filter |
| `filter/ApiKeyGatewayFilterFactory.java` | Route filter factory for API keys |
| `docker-compose.yml` | Local Redis |

Upstream calls use **https://httpbin.org** so you can run the gateway without deploying separate services.
