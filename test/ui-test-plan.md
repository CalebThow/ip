# Console UI test plan

## Program under test

- Launch command: `java -cp _temp/test-ui-classes chatbot.AgentCT`
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

### Test case 2: Add and list task types

**Aim:** Verify that todo, deadline, and event commands preserve their descriptions and timing strings, display the correct type icons, and update the task count.

**Inputs:**

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
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
     todo borrow book
____________________________________________________________
     Got it. I've added this task:
       [T][ ] borrow book
     Now you have 1 tasks in the list.
____________________________________________________________
     deadline return book /by Sunday
____________________________________________________________
     Got it. I've added this task:
       [D][ ] return book (by: Sunday)
     Now you have 2 tasks in the list.
____________________________________________________________
     event project meeting /from Mon 2pm /to 4pm
____________________________________________________________
     Got it. I've added this task:
       [E][ ] project meeting (from: Mon 2pm to: 4pm)
     Now you have 3 tasks in the list.
____________________________________________________________
     list
____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] borrow book
     2.[D][ ] return book (by: Sunday)
     3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
     bye
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
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
$ java -cp _temp/test-ui-classes chatbot.AgentCT
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

### Feature verification session

- Date: 2026-09-04
- Result: Passed (manual walkthrough; Java compilation and task-type output verified)

```text
$ java -cp _temp/test-ui-classes chatbot.AgentCT
[stdin]
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
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
     todo borrow book
____________________________________________________________
     Got it. I've added this task:
       [T][ ] borrow book
     Now you have 1 tasks in the list.
____________________________________________________________
     deadline return book /by Sunday
____________________________________________________________
     Got it. I've added this task:
       [D][ ] return book (by: Sunday)
     Now you have 2 tasks in the list.
____________________________________________________________
     event project meeting /from Mon 2pm /to 4pm
____________________________________________________________
     Got it. I've added this task:
       [E][ ] project meeting (from: Mon 2pm to: 4pm)
     Now you have 3 tasks in the list.
____________________________________________________________
     list
____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] borrow book
     2.[D][ ] return book (by: Sunday)
     3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
     bye
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
RESULT: PASSED
```

### Package-structure verification session

- Date: 2026-09-10
- Result: Passed (1 test; Java 25 compilation and console launch verified after moving classes to the `chatbot` package)

```text
$ java -cp _temp/test-ui-classes chatbot.AgentCT
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
