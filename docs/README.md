# Sophon User Guide

Sophon is a command-line chatbot that stores tasks typed by the user, marks
them as done, and lists them when requested.

## Adding Tasks

Type a task and press Enter. Sophon stores the task in memory for the current
session.

Example:

```text
read book
    ____________________________________________________________
     added: read book
    ____________________________________________________________
```

## Listing Tasks

Type `list` and press Enter to view the tasks added so far.

Example:

```text
list
    ____________________________________________________________
     Current tasks under observation:
     1.[ ] read book
     2.[ ] return book
    ____________________________________________________________
```

## Marking Tasks

Type `mark` followed by a task number to mark that task as done.

Example:

```text
mark 2
    ____________________________________________________________
     Acknowledged. This task is now complete:
       [X] return book
    ____________________________________________________________
```

## Exiting

Type `bye` and press Enter to end the conversation.
