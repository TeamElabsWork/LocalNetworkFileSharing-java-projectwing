title: [app] Set up repository: branch protection, labels, milestones and project board
milestone: 0 - Foundation
labels: module:app|difficulty:easy|priority:mvp|good first issue|help wanted
---
**Module:** `app` (M1/M8) · **Milestone:** 0 - Foundation · **Difficulty:** easy · **Priority:** MVP  
**Suggested branch:** `chore/repo-setup`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Prepare the GitHub repository so the team can work in parallel without breaking `main`.

## Tasks
- [ ] Push the baseline to GitHub (`main`).
- [ ] Replace the placeholder handles in `CODEOWNERS` with the real GitHub usernames of the module owners.
- [ ] Protect `main`: require a pull request, 1 approval, CODEOWNERS review and the `CI` check to pass.
- [ ] Run `scripts/create-issues.sh` (creates labels, milestones and all issues) if it has not been run yet.
- [ ] Create a GitHub Project board (Todo / In progress / In review / Done) and add all issues.
- [ ] Invite all teammates with write access.

## Files / classes
- `.github/workflows/ci.yml`
- `scripts/create-issues.sh`
- `CODEOWNERS`

## Acceptance criteria
- [ ] `main` cannot be pushed to directly.
- [ ] CI runs on every pull request and passes on the baseline.
- [ ] CODEOWNERS contains real usernames.

## Depends on
- None - can start right away.

## Notes
Maintainer task. Needs admin rights on the repository.

## Definition of done
- [ ] PR merged into `main` with `Closes #1`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
