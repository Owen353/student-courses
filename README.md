# Student Courses — Simulating a Relational Join in Plain Java

A minimal runnable Java program that reads student records and enrollment records from two text files, performs an equi-join manually in memory, and prints each student's courses.

The point isn't the feature itself — it's showing **what it costs to do this without a database**: the join has to be written by hand, there are no indexes, no type constraints, no transactions. The same result takes one `JOIN` in SQL.

```sql
SELECT s.name, s.major, e.cId
FROM Student s LEFT JOIN Enrollment e ON s.sId = e.sId;
```

---

## Project structure

```
.
├── Student.java              # Student entity: id, name, major
├── Enrollment.java           # Enrollment record: student id, course id
├── StudentCoursesDriver.java # Main program: read files, manual join, print
├── Students.txt              # Input data (create this yourself)
└── Enrolled.txt              # Input data (create this yourself)
```

## Input file format

Both input files are headerless CSV — comma-separated fields, one record per line.

**Students.txt** — `id,name,major`

```
1001,Alice,Computer Science
1002,Bob,Mathematics
1003,Carol,Physics
```

**Enrolled.txt** — `id,courseId`

```
1001,COMP352
1001,COMP353
1002,MATH204
```

Don't leave a trailing blank line at the end of either file — `split(",")` will produce too few fields and throw `ArrayIndexOutOfBoundsException`.

## Build and run

Requires JDK 8 or later. Both `.txt` files must sit in the **working directory** you run the program from, since the code uses relative paths.

```bash
javac Student.java Enrollment.java StudentCoursesDriver.java
java StudentCoursesDriver
```

## Sample output

```
Alice, Computer Science -> COMP352 COMP353
Bob, Mathematics -> MATH204
Carol, Physics -> no courses
```

A student with no enrollment records prints `no courses`, which is what a SQL `LEFT JOIN` does: the row from the left table appears in the result even when nothing on the right matches.

## How it works

Three steps:

1. Read `Students.txt` line by line with a `BufferedReader`, split on commas, and build `Student` objects into an `ArrayList`.
2. Do the same for `Enrolled.txt` to build the `Enrollment` list.
3. Nested loop: for each student, scan every enrollment record and print the course id wherever the student ids match.

The join is a textbook **nested-loop join**, O(n × m). A database would use a hash join or sort-merge join backed by an index to bring that down. There's no optimization here at all, so it slows down visibly once either list grows.

## Known limitations

These are deliberate simplifications, and each one is a direction to improve:

- **Performance**: bucketing enrollments into a `HashMap<Integer, List<String>>` first would make it O(n + m).
- **Parsing robustness**: `split(",")` can't handle commas or quotes inside a field, and nothing validates the line format.
- **Resource handling**: `close()` is called directly, so an exception mid-read leaves the reader open. Use try-with-resources instead.
- **Encapsulation**: entity fields are package-private; they should be `private` with getters.
- **Error handling**: `throws Exception` hands everything to the JVM, so a missing file just prints a stack trace.

## Suggested .gitignore

Build output and IDE config don't belong in the repository:

```gitignore
*.class
.classpath
.project
.settings/
bin/
```
