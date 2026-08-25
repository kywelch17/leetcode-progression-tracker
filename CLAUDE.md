# Project Overview

This is a full-stack application that provides a dashboard for tracking a
user's attempts at LeetCode problems.

## Domain

An Attempt represents a user's attempt at solving a LeetCode problem.

Attempts can eventually be created through:
- A form
- The AI chat interface

The AI chat interface is not currently implemented.

The `/model` directory contains the Attempt domain model. Inspect the code
rather than assuming the structure of Attempt.

## Development Instructions

### Before Making Changes

Read-only operations do not require approval. You may inspect files, search
the codebase, run tests, run builds, and execute other non-mutating commands
without asking.

Before making any change to the project:

1. Explain what you intend to change.
2. Identify the files you intend to change.
3. Explain why the change is necessary.
4. Explain any important tradeoffs or alternatives.

Wait for my approval before making the change.

### Git

Never commit code.
Never push code.

I will handle commits and pushes myself.

## Parking Lot

These are ideas under consideration, not requirements. Do not implement them
unless explicitly requested.

- Implement the AI chat feature.
- Evaluate whether the Problem entity/table should be removed in favor of
  retrieving problems directly from the LeetCode GraphQL API.
- Evaluate whether LeetCode problems should be cached.
- Consider having the AI chat coach users through their problem-solving
  process rather than simply providing answers.
- Evaluate whether PostgreSQL is necessary.

## TODO

- [ ] Create tests
- [ ] Design frontend using the Figma design
- [ ] Add user authentication
- [ ] Deploy frontend to Render