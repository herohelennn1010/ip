# CS2103/T iP Requirements Guide

This is the living project guide for assistant work in this repository.
Before making future project changes, check this file and update it when the course pages change.

Source checked: 2026-08-20
Primary source: https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-w2.html
Related source: https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-grading.html

## Always Remember

- This is the CS2103/T individual project, based on Project Duke.
- Follow course instructions closely because grading scripts can miss work when names, paths, branches, tags, or repo setup deviate from the expected form.
- Do not clump all iP work into one short burst. Some increments need to be done weekly for full credit.
- It is fine to stay slightly ahead, but not more than one week ahead of the schedule unless the user explicitly decides otherwise.
- Use Java 25 for running and checking the project.
- Keep code in `[project root]/src`; grading scripts look there.
- Do not commit or push unless the user explicitly asks.

## Week 2 Tasks

Deadline for this week's iP tasks: Friday, 2026-08-21, 16:00.
The course says catching up within one more week after the deadline is not penalized, but the target should still be the stated weekly deadline.

Week 2 setup requirements:

- Read the iP Overview and iP Grading pages.
- Ensure prerequisites are prepared: Java/programming language setup, Git, GitHub, AI coding tools, and IDE.
- Fork the upstream repository from `https://github.com/NUS-CS2103-AY2627-S1/ip`.
- Keep the fork name as `ip`.
- Keep the default branch name as `master`.
- Do not change the source location away from `[project root]/src`.
- When forking, uncheck `Copy the master branch only` so the full repo is copied.
- Enable Issues on the GitHub fork.
- Clone the fork locally.
- Avoid placing Git-controlled files in cloud-synced folders such as OneDrive or Dropbox.
- Run `Duke.java` to verify the setup works.

Week 2 implementation increments, in order:

- `Level-0`: Rename, greet, exit.
- `Level-1`: Echo.
- `Level-2`: Add and list tasks.
- `Level-3`: Mark tasks as done.
- `Level-4`: Support todos, events, and deadlines.
- `Level-5`: Handle errors.
- `Level-6`: Delete tasks.
- `A-Enums`: Use enums if applicable.

## Increment Workflow

- Commit at important points. Minimally, commit after completing each increment.
- After completing each increment, tag the exact commit with the exact increment ID, e.g. `Level-2`.
- Use lightweight tags unless the user requests annotated tags.
- Push code and tags to the user's fork only when the user asks.
- Remember that Git does not push tags automatically.
- Do not commit generated `.class` files or other build outputs.
- If a bug is found after an increment is tagged, fix it in a later commit rather than rewriting published history unless the user explicitly chooses otherwise.

## Grading Bars

The iP is worth 15 marks. The grading page says students get full marks if all bars are met, but less than half marks if any bar is missed. Treat every bar below as important.

Implementation, 10 marks:

- More than 90% of non-optional, non-if-applicable deliverables are completed in the final version.
- If a requirement asks only for a minimal version, that minimal version is enough for grading, though going further can be useful for learning.
- The final iP has a GUI at least as good as part 4 of the JavaFX tutorial.
- At least two optional increments using AI assistance are completed as stated in the Week 6 iP instructions.
- No major bugs.
- Reasonable OOP is used, including some inheritance and sensible class division such as `Ui`, `Storage`, `Parser`, `Todo`, `Deadline`, and `Event`.
- At least half of public methods/classes have Javadoc comments.
- Code quality is reasonable: no blatant Java or Git convention violations, no chunks of commented-out code, and no very long methods or deeply nested code.
- At least some errors are handled using exceptions.
- At least two methods have good JUnit unit tests.

Project management, 2 marks:

- Submit some deliverables in at least 4 of the 5 iP weeks, Week 2 through Week 6.
- Follow weekly requirements such as Git/GitHub usage for each increment and peer reviews in at least 4 weeks.
- For full marks, the last 5 iP commit message subjects must comply with the course Git convention so the Git Standard tag is green.
- If the Git Standard tag is not green, prefer adding small legitimate code tweaks in new commits instead of rewriting past commit messages and force-pushing.

Documentation, 3 marks:

- The product website and user guide should guide users sufficiently.
- Cover all non-trivial features.
- Avoid major formatting errors in the published view.

## Assistant Working Rules

- Before implementing a feature, map it to the current iP increment and grading bar.
- Prefer small, understandable Java/OOP steps that fit the student's learning stage.
- Add Javadocs for classes and for public or nontrivial methods when adding or changing code.
- Keep code neat and avoid speculative abstractions.
- Use exceptions for meaningful error handling once the project reaches error-handling increments.
- Add or update focused JUnit tests when behavior becomes nontrivial.
- Preserve grading-script-sensitive conventions: repo name `ip`, branch `master`, source path `src`, exact increment tag names.
- Check this guide against the official course pages whenever the user asks about a new week or grading-related change.
