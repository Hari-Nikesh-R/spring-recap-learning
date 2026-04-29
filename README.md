# Why Spring Batch even exist?

Imagine you have 10Lakh (1M) records in DB.
We need to 
  - Read them
  - Process (Calculate something)
  - Write them in another DB.

If we do this in a normal Spring boot API:
```java
List<Object> data = repository.findAll();
```
## Problem
1. Memory crash
2. No retry if failure.
3. No monitoring
4. No parallel processing.
5. No Scheduling.

## We Use Spring Batch
Spring Batch is designed for 
Processing large volume of data in reliable, scalable, fault tolerant way.

## Real world use cases?
1. Bank transaction processing.
2. Salary processing.
3. Large report generation.
4. Data migration.
etc..

## Behind the hood, Core concept 
```
job -> step -> Chunk -> Item
```

### Job
The entire batch process

### Step
One phase of Job.

### Chunk
Processing the data in small batches (100 records)

### Item
Single record

## We will do!
```
Read users from Db(50K) -> Filter active user -> Write in a CSV
```

```
Scheduler -> Job -> 
Step 1 (Read DB) Reader
Step 2 (Process) Processor
Step 3 (Write them to the Output CSV) (Regular function)
```





<br>
<br><br><br><br><br><br><br><br><br><br><br>
<br><br><br><br><br><br><br><br><br><br><br>
asdasd
