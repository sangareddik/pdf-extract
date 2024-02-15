**ESLint and Prettier**

_ESLint is a static code analysis tool for identifying problematic patterns found in JavaScript code. _

_Prettier is an opinionated code formatter_

**Linting and formatting commands:**

These commands to be used for fixing any auto linting fixes and auto formatting the code

| Lint   | npm run lint   |
| ------ | -------------- |
| Format | npm run format |

**ESLint Rules to be followed:**


| Rule                 | Description                                                                                                                                                                                                            | Reference                                                                                                |
| -------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| eqeqeq               | This rule is aimed at eliminating the type-unsafe equality operators. Example: foo === truebananas !== 1                                                                                                               | [https://eslint.org/docs/rules/eqeqeq](https://eslint.org/docs/rules/eqeqeq)                             |
| no-undef             | This rule can help you locate potential ReferenceErrors resulting from misspellings of variable and parameter names, or accidental implicit globals                                                                    | [https://eslint.org/docs/rules/no-undef](https://eslint.org/docs/rules/no-undef)                         |
| no-caller            | This rule is aimed at discouraging the use of deprecated and sub-optimal code by disallowing the use of arguments.caller and arguments.callee                                                                          | [https://eslint.org/docs/rules/no-caller](https://eslint.org/docs/rules/no-caller)                       |
| no-bitwise           | This rule disallows bitwise operators.                                                                                                                                                                                 | [https://eslint.org/docs/rules/no-bitwise](https://eslint.org/docs/rules/no-bitwise)                     |
| no-cond-assign       | This rule disallows ambiguous assignment operators in test conditions of if, for, while, and do...while statements.                                                                                                    | [https://eslint.org/docs/rules/no-cond-assign](https://eslint.org/docs/rules/no-cond-assign)             |
| no-return-await      | This rule aims to prevent a likely common performance hazard due to a lack of understanding of the semantics of async function.                                                                                        | [https://eslint.org/docs/rules/no-return-await](https://eslint.org/docs/rules/no-return-await)           |
| no-duplicate-imports | This rule requires that all imports from a single module exists in a single import statement.                                                                                                                          | [https://eslint.org/docs/rules/no-duplicate-imports](https://eslint.org/docs/rules/no-duplicate-imports) |
| no-console           | This rule disallows calls to methods of the console object.Note: Only Warn and Error consoles are allowed                                                                                                              | [https://eslint.org/docs/rules/no-console](https://eslint.org/docs/rules/no-console)                     |
| prefer-template      | This rule is aimed to flag usage of + operators with strings.                                                                                                                                                          | [https://eslint.org/docs/rules/prefer-template](https://eslint.org/docs/rules/prefer-template)           |
| object-shorthand     | This rule enforces the use of the shorthand syntax. This applies to all methods (including generators) defined in object literals and any properties defined where the key name matches name of the assigned variable. | [https://eslint.org/docs/rules/object-shorthand](https://eslint.org/docs/rules/object-shorthand)         |
| prefer-const         | This rule is aimed at flagging variables that are declared using let keyword, but never reassigned after the initial assignment.                                                                                       | [https://eslint.org/docs/rules/prefer-const](https://eslint.org/docs/rules/prefer-const)                 |
| no-var               | This rule is aimed at discouraging the use of var and encouraging the use of const or let instead.                                                                                                                     | [https://eslint.org/docs/rules/no-var](https://eslint.org/docs/rules/no-var)                             |
| max-len              | This rule enforces a maximum line length to increase code readability and maintainability. The length of a line is defined as the number of Unicode characters in the line.                                            | [https://eslint.org/docs/rules/max-len](https://eslint.org/docs/rules/max-len)                           |
