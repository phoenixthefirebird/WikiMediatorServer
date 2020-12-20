# mp3 Feedback

## Grade: 1.5

| Compilation | Timeout | Duration |
|:-----------:|:-------:|:--------:|
|Yes|No|35.52|

## Test Results
### cpen221.mp3.Task2Grader
| Test Status | Count |
| ----------- | ----- |
|errors|0|
|failures|1|
|tests|4|
|skipped|0|
#### Failed Tests
1. `testUpdate (java.lang.AssertionError: FSFTBuffer: concurrent update failure)`
### cpen221.mp3.Task1Grader
| Test Status | Count |
| ----------- | ----- |
|errors|0|
|failures|3|
|tests|10|
|skipped|0|
#### Failed Tests
1. `testTouch (java.lang.AssertionError: FSFTBuffer: Item should not have timed out)`
1. `testCapacityEvictionAndTimeout (java.lang.AssertionError: FSFTBuffer: item should have timed out)`
1. `testUpdate (java.lang.AssertionError: expected:<cpen221.mp3.Text@cfac65> but was:<cpen221.mp3.Text@ceff206>)`

## Comments

FSFTBuffer.java:8:	Avoid unused imports such as 'java.util.stream.Collectors'

FSFTBuffer.java:18:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:18:	Private field 'globalTimer' could be made final; it is only initialized in the declaration or constructor.

FSFTBuffer.java:19:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:19:	Private field 'increments' could be made final; it is only initialized in the declaration or constructor.

FSFTBuffer.java:21:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:22:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:23:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:25:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:25:	Private field 'bufferContents' could be made final; it is only initialized in the declaration or constructor.

FSFTBuffer.java:26:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:26:	Private field 'bufferReversed' could be made final; it is only initialized in the declaration or constructor.

FSFTBuffer.java:27:	Found non-transient, non-static member. Please mark as transient or provide accessors.

FSFTBuffer.java:27:	Private field 'timers' could be made final; it is only initialized in the declaration or constructor.

FSFTBuffer.java:82:	Found 'DU'-anomaly for variable 'min' (lines '82'-'93').

FSFTBuffer.java:83:	Found 'DD'-anomaly for variable 'id' (lines '83'-'87').

FSFTBuffer.java:86:	Found 'DU'-anomaly for variable 'min' (lines '86'-'93').

FSFTBuffer.java:87:	Found 'DD'-anomaly for variable 'id' (lines '87'-'87').

FSFTBuffer.java:104:	Potential violation of Law of Demeter (method chain calls)

FSFTBuffer.java:121:	Potential violation of Law of Demeter (method chain calls)

LoosePackageCoupling	-	No packages or classes specified

## Test Coverage
### FSFTBuffer
| Metric | Coverage |
| ------ | -------- |
|BRANCH_MISSED|4|
|BRANCH_COVERED|8|
|LINE_COVERED|43|
|LINE_MISSED|6|

## Checkstyle Results
### `FSFTBuffer.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 4 | None | `Using the '.*' form of import should be avoided - java.util.*.` |
| 8 | 8 | `Unused import - java.util.stream.Collectors.` |
| 70 | None | `Expected @return tag.` |
| 70 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 70 | 39 | `Expected @param tag for 't'.` |
| 71 | 9 | `'if' is not followed by whitespace.` |
| 74 | 34 | `',' is not followed by whitespace.` |
| 75 | 29 | `',' is not followed by whitespace.` |
| 81 | 18 | `'private' modifier out of order with the JLS suggestions.` |
| 103 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 103 | 31 | `'(' is preceded with whitespace.` |
| 104 | 9 | `'if' is not followed by whitespace.` |
| 104 | 50 | `'{' is not preceded with whitespace.` |
| 120 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 121 | 9 | `'if' is not followed by whitespace.` |
| 137 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 138 | 9 | `'if' is not followed by whitespace.` |
| 149 | 22 | `'public' modifier out of order with the JLS suggestions.` |
| 151 | 17 | `'<' is preceded with whitespace.` |
| 152 | 13 | `'for' is not followed by whitespace.` |
| 152 | 44 | `'{' is not preceded with whitespace.` |
| 153 | 17 | `'if' is not followed by whitespace.` |
| 153 | 50 | `'{' is not preceded with whitespace.` |
| 159 | 13 | `'for' is not followed by whitespace.` |
| 159 | 40 | `'{' is not preceded with whitespace.` |
| 4 | None | `Using the '.*' form of import should be avoided - java.util.*.` |
| 8 | 8 | `Unused import - java.util.stream.Collectors.` |
| 70 | None | `Expected @return tag.` |
| 70 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 70 | 39 | `Expected @param tag for 't'.` |
| 71 | 9 | `'if' is not followed by whitespace.` |
| 74 | 34 | `',' is not followed by whitespace.` |
| 75 | 29 | `',' is not followed by whitespace.` |
| 81 | 18 | `'private' modifier out of order with the JLS suggestions.` |
| 103 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 103 | 31 | `'(' is preceded with whitespace.` |
| 104 | 9 | `'if' is not followed by whitespace.` |
| 104 | 50 | `'{' is not preceded with whitespace.` |
| 120 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 121 | 9 | `'if' is not followed by whitespace.` |
| 137 | 18 | `'public' modifier out of order with the JLS suggestions.` |
| 138 | 9 | `'if' is not followed by whitespace.` |
| 149 | 22 | `'public' modifier out of order with the JLS suggestions.` |
| 151 | 17 | `'<' is preceded with whitespace.` |
| 152 | 13 | `'for' is not followed by whitespace.` |
| 152 | 44 | `'{' is not preceded with whitespace.` |
| 153 | 17 | `'if' is not followed by whitespace.` |
| 153 | 50 | `'{' is not preceded with whitespace.` |
| 159 | 13 | `'for' is not followed by whitespace.` |
| 159 | 40 | `'{' is not preceded with whitespace.` |

