# Assessment System

## :confetti_ball: Features
- Seamless sign in via `Google SignIn`
- `Projects` ⇒ Teach anything you want
- - View & Search projects created by you and others
- - Create, Update & Delete your projects
- `Question` ⇒ MCQ which can be used anywhere
- - View & Search questions created by you and others
- - Create your own MCQ, with **custom** number of options
- `Quiz` ⇒ Create assessments for your teachings the smooth way
- - View & Search Quiz created by you and others
- - Create your own Quiz, by browsing through our pool of questions
- `Exam` ⇒ Test & Analyse your results over various tests
- - Filter through exams created by everyone
- - Take exam, *notice that in the network tab, correctOptions aren't exposed*
- - After attempting exam, view results ⇒ Percentile + Percentage *(doughnut)* chart

## :cd: Installation

### Backend [Repo Link](https://github.com/nilshah98/assessment-management)
- Clone and Import this project, in the Java IDE of your choice.
- Build the project.
- - Errors after building ⇒ `log` not found. Refer [here](https://stackoverflow.com/questions/16627751/building-with-lomboks-slf4j-and-eclipse-cannot-find-symbol-log)
- Config for `SQL Server` can be found at [here](https://github.com/nilshah98/assessment-management/blob/master/src/main/resources/application.yaml)
- To config `CORS` and `CSRF`, Security config can be found [here](https://github.com/nilshah98/assessment-management/blob/master/src/main/java/com/accolite/assessmentmanagement/config/Security.java)

### Frontend [Repo Link](https://github.com/nilshah98/Assessment-UI)
- Clone and navigate to the repo
- Install dependencies ⇒ `npm i`
- - For developer dependencies, use `--dev` flag
- Proxy settings can be found [here](https://github.com/nilshah98/Assessment-UI/blob/master/proxy.conf.json)
- - Config as per your backend settings, by default serve on `port 8080`


## :movie_camera: Screen Recordings

### Giving Exams + Results
![](docs/assets/recordings/GiveExam.gif)

> Complete list of screen recordings can be found [here](docs/ScreenRecordings.md)

## :camera: UI Screenshots

### Home
![](docs/assets/screenshots/Home.png)

> Complete list of screen recordings can be found [here](docs/Screenshots.md)

## :file_folder: File Structure
### Backend
Main folder at `src/main/java`
- Backend divided in 5 modules as per Spring MVC framework
1. `Config` ⇒ All the configuration for the project, like Security
2. `Model` ⇒ Model structure for various database entities
3. `Repository` ⇒ JPA Link with Models
4. `Resources` ⇒ Controllers, to route each query
5. `Services` ⇒ To serve each request coming through the `Resources`
> Flow ⇒ Resources > Services > Repository > Model

### Frontend
- `libs` ⇒ Contain all the shared module, namely `Material` and `Core-Data`. Placed here, for easier import and management
- - `Material` ⇒ Manage import and export of angular material modules at one place
- - `Core-Data` ⇒ `core-data/src/lib/` contains all the injectable services
- `apps/dashboard/src` ⇒ Component folder, contains `assets` and `app`
- - `app` ⇒ Contains all components


## :warning: Note
Open for `enhancements` & `bug-fixes` ! 

