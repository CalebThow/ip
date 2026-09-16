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
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
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
____________________________________________________________
     Got it. I've added this task:
       [T][ ] borrow book
     Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
     Got it. I've added this task:
       [D][ ] return book (by: Sunday)
     Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
     Got it. I've added this task:
       [E][ ] project meeting (from: Mon 2pm to: 4pm)
     Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] borrow book
     2.[D][ ] return book (by: Sunday)
     3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
```

### Test case 3: Reject invalid commands

**Aim:** Verify that an unrecognized command is rejected and is not added to the task list.

**Inputs:**

```text
dance
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
____________________________________________________________
     Please enter a valid command!
     Examples:
       todo <description>
       deadline <description> /by <time>
       event <description> /from <time> /to <time>
       list
       mark <number>
       unmark <number>
____________________________________________________________
     Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
```

### Test case 4: Reject todo command without a description

**Aim:** Verify that `todo ` without a description displays guidance and does not add a task.

**Inputs:**

```text
todo 
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
____________________________________________________________
     Please provide a task description!
     Format: todo <description>
     Example: todo Play Video Games
____________________________________________________________
     Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
```

### Test case 5: Reject deadline and event commands without descriptions

**Aim:** Verify that deadline and event commands with blank descriptions are rejected without adding tasks.

**Inputs:**

```text
deadline  /by Sunday
event  /from Monday /to Tuesday
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
____________________________________________________________
     Please provide a task description!
     Format: todo <description>
     Example: todo Play Video Games
____________________________________________________________
____________________________________________________________
     Please provide a task description!
     Format: todo <description>
     Example: todo Play Video Games
____________________________________________________________
____________________________________________________________
     Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
```

### Test case 6: Preserve state across interleaved valid and invalid commands

**Aim:** Verify that invalid commands and invalid task numbers do not add, remove, or alter tasks created by valid commands.

**Inputs:**

```text
todo buy milk
dance
list
mark 0
mark 1
unmark 99
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
____________________________________________________________
     Got it. I've added this task:
       [T][ ] buy milk
     Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
     Please enter a valid command!
     Examples:
       todo <description>
       deadline <description> /by <time>
       event <description> /from <time> /to <time>
       list
       mark <number>
       unmark <number>
____________________________________________________________
____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] buy milk
____________________________________________________________
____________________________________________________________
     Sorry, that task number does not exist.
____________________________________________________________
____________________________________________________________
     Nice! I've marked this task as done:
       [X] buy milk
____________________________________________________________
____________________________________________________________
     Sorry, that task number does not exist.
____________________________________________________________
____________________________________________________________
     Here are the tasks in your list:
     1.[T][X] buy milk
____________________________________________________________
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
```

### Test case 7: Reject malformed command formats

**Aim:** Verify that incomplete task commands are rejected and do not affect the task list before or after a valid command.

**Inputs:**

```text
deadline return book
todo read notes
event team meeting /from Monday
list
todo
list
bye
```

### Test case 8: Save changed tasks to disk

**Aim:** Verify that adding tasks and marking a task as done automatically writes the current task list to `data/duke.txt`.

**Inputs:**

```text
todo buy milk
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
mark 1
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
____________________________________________________________
     Got it. I've added this task:
       [T][ ] buy milk
     Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
     Got it. I've added this task:
       [D][ ] return book (by: Sunday)
     Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
     Got it. I've added this task:
       [E][ ] project meeting (from: Mon 2pm to: 4pm)
     Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
     Nice! I've marked this task as done:
       [X] buy milk
____________________________________________________________
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
```

**Expected save file contents:**

```text
T | 1 | buy milk
D | 0 | return book | Sunday
E | 0 | project meeting | Mon 2pm | 4pm
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
____________________________________________________________
     Please enter a valid command!
     Examples:
       todo <description>
       deadline <description> /by <time>
       event <description> /from <time> /to <time>
       list
       mark <number>
       unmark <number>
____________________________________________________________
____________________________________________________________
     Got it. I've added this task:
       [T][ ] read notes
     Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
     Please enter a valid command!
     Examples:
       todo <description>
       deadline <description> /by <time>
       event <description> /from <time> /to <time>
       list
       mark <number>
       unmark <number>
____________________________________________________________
____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] read notes
____________________________________________________________
____________________________________________________________
     Please provide a task description!
     Format: todo <description>
     Example: todo Play Video Games
____________________________________________________________
____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] read notes
____________________________________________________________
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

### Latest test session

- Date: 2026-09-17
- Result: UI runner executed 1 test; failed because the temporary expected transcript still included an echoed `bye` command, while the actual program does not echo commands. The direct save verification passed.

```text
=== Test 1: Exit with bye ===
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
____________________________________________________________
     Goodbye! Hope you have an amazing day!
____________________________________________________________
RESULT: FAILED
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
