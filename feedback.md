# mp3 Feedback

## Grade: 4.0

## Comments
Tasks 1 and 2 had good implementations. Tasks 3 and 4 were acceptable (defects exist). Task 5 was not implemented properly.

Code documentation is good. Code organization has room for improvement.

+++
RIs and AFs that were written are quite good, but some classes are missing them.  Specs are consistently good and included in all files - so great job here.  Note that in the future, public constructors should have specs, too.  Some code smells such as code repetition and comment artifacts.  The implementation is also quite complex, so commenting seemed insufficient in some areas.  There are also messy things like that killer if() statement (whose condition takes 6 lines) that could be logically simlified.  Naming is good.  As an aside, I thought the WikiMediator implementation is clever.  Contribs are quite imbalanced, and contrib file cannot address the imbalances because it is empty.

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

