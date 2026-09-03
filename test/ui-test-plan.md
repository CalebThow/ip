# Console UI test plan

## Program under test

- Launch command: `java -cp _temp/test-ui-classes ip.AgentCT`
- Working directory: repository root
- Java version: 25.0.4.1
- Other assumptions: `AgentCT.java` and `Task.java` are compiled into `_temp/test-ui-classes` before testing.

## Test cases

### Test case 1: Exit with bye

**Aim:** Verify that the chatbot accepts `bye` and displays its goodbye message before terminating.

**Inputs:**

```text
bye
```

**Expected output:**

```text
____________________________________________________________
    _                    _    ____ _____
   / \   __ _  ___ _ __ | |_ / ___|_   _|
  / _ \ / _` |/ _ \ '_ \| __| |     | |
 / ___ \ (_| |  __/ | | | |_  |___  | |
/_/   \_\__, |\___|_| |_|\__|\____| |_|
        |___/
____________________________________________________________
Welcome! I'm AgentCT.
How may I help you?
____________________________________________________________
     bye
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
```

## Latest test session

- Date: 2026-09-04
- Result: Passed (1 test; rerun after agent workflow update)

```text
$ java -cp _temp/test-ui-classes ip.AgentCT
[stdin]
bye
[output]
____________________________________________________________
    _                    _    ____ _____
   / \   __ _  ___ _ __ | |_ / ___|_   _|
  / _ \ / _` |/ _ \ '_ \| __| |     | |
 / ___ \ (_| |  __/ | | | |_  |___  | |
/_/   \_\__, |\___|_| |_|\__|\____| |_|
        |___/
____________________________________________________________
Welcome! I'm AgentCT.
How may I help you?
____________________________________________________________
     bye
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
RESULT: PASSED
ALL TESTS PASSED (1)
```
