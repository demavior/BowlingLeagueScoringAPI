# BowlingLeagueScoringAPI

## Overview
BowlingLeagueScoringAPI is a web-based system designed to track and manage bowling scores for a two-person team league. The application enables players, teams, and league administrators to maintain records of games, player performance, and league standings.

## Functionality

### User Management
- **Roles:** Supports different roles, such as league administrators, team captains, and players.
- **Authentication:** Users log in using email and password.
- **User Profiles:** Manage personal details such as first name, last name, and middle name.

### League Management
- **League Details:** Manage league information, including name, description, start and end dates, and registration periods.
- **Team Registration:** Manage team creation and association with specific leagues, including captain assignment.

### Player Management
- **Player Profiles:** Players are linked to user accounts and teams, with individual averages and handicaps tracked.
- **Team Assignment:** Players are assigned to teams, and team captains are designated.

### Game Tracking
- **Game Records:** Log game details, including date, associated team, score, and game week.
- **Frame Scoring:** Track frame-specific details such as knocked pins, spares, and strikes.

### Scoring and Statistics
- **Player Averages:** Calculate players' average scores across games.
- **Handicap Tracking:** Maintain handicaps to ensure fair competition.
- **League Rankings:** Generate rankings based on points won, points lost, total scores, and win percentages.

## Usage

1. **Database Setup:** Ensure the database schema is created and operational.
2. **API Deployment:** Deploy the API on a server or cloud environment to handle requests.
3. **Frontend Integration:** Use REST API endpoints to create a user interface for managing leagues, teams, players, and games.
4. **Track Games:** Record and retrieve game data to analyze performance and maintain league standings.

---
This API is built to streamline bowling league operations and enhance the experience for administrators and participants.
