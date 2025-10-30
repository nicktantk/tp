---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# InSight Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**
This project was **forked** from [se-edu/addressbook-level3](https://github.com/se-edu/addressbook-level3) under the **MIT License**. It extends the original work with additional features and functionalities inspired by CRM-style client and booking management systems.

### Idea Inspiration
- The concept and design of this application were inspired by **AI-assisted brainstorming** and various **CRM (Customer Relationship Management) tools**.
- The goal was to adapt contact and booking management ideas into a personalized, extensible system.

### Third-Party Libraries
- **JavaFX** — used for building the graphical user interface.  
  [https://openjfx.io](https://openjfx.io)
- **Jackson** — used for JSON data serialization and deserialization.  
  [https://github.com/FasterXML/jackson](https://github.com/FasterXML/jackson)
- **JUnit 5** — used for unit and integration testing.  
  [https://junit.org/junit5](https://junit.org/junit5)

### Development Tools
- **Gradle** — for build automation and dependency management.  
  [https://gradle.org](https://gradle.org)
- **GitHub Pages** — for hosting project documentation.  
  [https://pages.github.com](https://pages.github.com)
- **PlantUML** — for generating UML diagrams used in documentation.  
  [https://plantuml.com](https://plantuml.com)
- **MarkBind** — for authoring and publishing the user and developer guides.  
  [https://markbind.org](https://markbind.org)
- **GitHub Actions (CI)** — for continuous integration and automated build testing.  
  [https://github.com/features/actions](https://github.com/features/actions)

### AI Assistance
- **ChatGPT (OpenAI)** — assisted with idea generation, conceptual explanations, and documentation drafting.  
  [https://chat.openai.com](https://chat.openai.com)
- **GitHub Copilot** — provided inline code suggestions and boilerplate generation.  
  [https://github.com/features/copilot](https://github.com/features/copilot)\

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI Component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The **UI component** is responsible for the graphical user interface of the application.  
It is built using the **JavaFX** framework. The layout of each UI part is defined in a corresponding `.fxml` file located in the `src/main/resources/view` folder.

#### Overview

The UI consists of a `MainWindow` that is composed of several parts, such as:

| Component | Description | FXML File |
|------------|--------------|-----------|
| `CommandBox` | Receives user input for command execution. | `CommandBox.fxml` |
| `ResultDisplay` | Shows feedback messages for user commands. | `ResultDisplay.fxml` |
| `PersonListPanel` | Displays a scrollable list of all client entries as `PersonCard`s. | `PersonListPanel.fxml` |
| `BookingListPanel` | Displays a scrollable list of all booking entries as `BookingCard`s. | `BookingListPanel.fxml` |
| `StatusBarFooter` | Shows the save location and entity counts. | `StatusBarFooter.fxml` |
| `MainWindow` | Acts as the root container tying all components together. | `MainWindow.fxml` |

All these components (including `MainWindow`) inherit from the abstract `UiPart` class, which encapsulates common functionality for JavaFX UI elements.


#### Layout Structure

The **MainWindow layout** (`MainWindow.fxml`) uses a vertical box (`VBox`) as its root container. Within it:

- A **menu bar** provides quick access to *Help* and *Exit* actions.
- The **command box** and **result display** occupy fixed vertical spaces at the top.
- The central section is an `HBox` named `container`, containing:
    - A **Client panel** (`personListPanelPlaceholder`) for displaying `PersonCard`s.
    - A **Booking panel** (`bookingListPanelPlaceholder`) for displaying `BookingCard`s.
- A **status bar** sits at the bottom showing save location and number of entities.

Each list panel (`PersonListPanel` and `BookingListPanel`) observes an `ObservableList` from the `Model`. Whenever the data changes (e.g., when a command modifies clients or bookings), the corresponding list view automatically updates via JavaFX’s observable bindings.

#### Responsibilities

The UI component:
- **Executes user commands** by calling `Logic#execute(String commandText)`.
- **Listens for changes** to the `Model` through observable bindings to ensure UI consistency.
- **Displays up-to-date information** about both clients (`Person`) and bookings (`Booking`).
- **Maintains references** to the `Logic` component for command execution and `Model` data retrieval.

#### Design Considerations

- **Parallel panels:** The `PersonListPanel` and `BookingListPanel` allow users to view client and booking information side by side.
- **Extensibility:** Additional panels (e.g., statistics or calendar view) can be added by following the same `UiPart` structure.
- **Consistency:** The `BookingCard` mirrors the structure of `PersonCard` for a cohesive visual experience.


### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage Component

**API:** [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The **Storage component** saves and loads data from disk in **JSON format**, including both address book and user preferences.

#### Responsibilities
- Reads/writes `Person`, `Booking`, and `Tag` objects using Jackson.
- Implements both `AddressBookStorage` and `UserPrefStorage` interfaces.
- Performs validation to ensure data integrity before loading into the model.

#### Error Handling & Validation

During deserialization (`JsonSerializableAddressBook#toModelType()`), the following checks ensure data consistency:

| Rule | Description | Exception |
|------|--------------|-----------|
| **Duplicate Person** | A duplicate `Person` entry exists. | `IllegalValueException(MESSAGE_DUPLICATE_PERSON)` |
| **Duplicate Booking** | A duplicate `Booking` entry exists. | `IllegalValueException(MESSAGE_DUPLICATE_BOOKING)` |
| **Missing Client** | A booking’s `Name` does not exist in the person list. | `IllegalValueException(MESSAGE_MISSING_CLIENT)` |

These prevent corrupted data or orphaned bookings from being loaded.

> 💡 **Note:** The storage layer guarantees that every booking corresponds to an existing client and that duplicates are rejected before data reaches the model.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Small photography/videography business owners who sell services directly to clients or other businesses
* Spend more time than they'd like juggling admin tasks (scheduling, chasing payments, tracking conversations) instead of focusing on shoots or editing.
* Values simplicity and tools that fit naturally into their creative flow
* A fast typer
* Prefers typing to mouse interactions
* Needs to manage a large number of clients, influencers, and other stakeholders

**Value proposition**:<br>
**For** Small photography/videography businesses who sell services (not products) directly to clients/businesses  
**Who are** dissatisfied with client details scattered across email, WhatsApp, and spreadsheets, making it hard to track who booked when, what package they purchased, specific requests, theme etc.  
**Our product is a** specially designed client relationship management system  
**That provides** booking history, loyalty tracking, trend/influencer monitoring, and client feedback storage in one place  
**Unlike** big enterprise CRMs (too costly, bloated) or plain address books (too limited, no sales context),  
**We offer** a simple, creative-friendly tool that helps photographers deliver what clients really want — shoots inspired by current trends and influencers.  

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​               | I can …​                                | So that I can…​                                             |
|----------|-----------------------|-----------------------------------------|-------------------------------------------------------------|
| `* * *`  | user                  | add client contact information          | have all my contacts in one place                           |
| `* * *`  | user                  | delete client contact information       | remove unnecessary contacts                                 |
| `* * *`  | user                  | edit client contact information         | correct errors in original entries                          |
| `* * *`  | user                  | add bookings for each client            | track past and upcoming bookings with my clients            |
| `* * *`  | user                  | mark bookings                           | track which bookings have been completed                    |
| `* * *`  | user                  | view booking details                    | be informed and prepared with upcoming bookings             |
| `* * *`  | user                  | edit bookings                           | update the booking if a change is discussed with the client |
| `* * *`  | user                  | delete bookings                         | remove unwanted bookings                                    |
| `* * *`  | new user              | view available commands                 | be reminded of commands available if I forget them          |
| `* *`    | time-constrained user | search for a client quickly             | save time searching for client details                      |
| `* *`    | detail-oriented user  | add tags to bookings                    | record additional details for the booking                   |
| `* *`    | new user              | clear sample data with a simple command | start afresh with my own clients                            |
| `* *`    | user                  | add tags to clients                     | filter and group clients                                    |
| `* *`    | user                  | undo my last action                     | rectify a mistake quickly                                   |
| `* *`    | user                  | archive inactive clients                | main client list is clean                                   |
| `* *`    | user                  | set booking priority                    | manage my workload                                          |
| `* *`    | user                  | reschedule bookings easily              | handle client changes quickly                               |
| `*`      | busy user             | get reminded about my upcoming bookings | not miss important appointments                             |
| `*`      | user                  | see booking statistics                  | manage and plan for future workload                         |
| `*`      | user                  | view a dashboard of upcoming bookings   | plan my schedule for the near future                        |
| `*`      | user                  | view recent clients                     | find them easily                                            |

### Use cases

(For all use cases below, the **System** is `InSight` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: Add a Tag to a Client**

**Purpose:** Allow the user to add a tag to an existing client after finding the client.

**MSS**
1. User requests to list clients.
2. System displays a list of clients.
3. User searches for a specific client by name or status.
4. System displays matching client(s).
5. User requests to add a tag to a client of choice from the displayed client(s).
6. System saves the tag under the client’s profile.

   Use case ends.

**Extensions**
* 3a. No clients match the search query.
    * 3a1. System shows a message indicating no clients found.  
      Use case ends.

* 5a. User selects an invalid client index.
    * 5a1. System shows an error message.  
      Use case resumes from step 5.


**Use Case: Edit Client Booking Details**

**Purpose:** Allow the user to edit details for a client’s existing booking.

**MSS**
1. User requests to list clients.
2. System displays a list of clients.
3. User requests to view a selected client's bookings from the list.
4. System shows the client’s bookings.
5. User selects a booking to edit and inputs edited booking details.
6. System saves the updated booking details.

   Use case ends.

**Extensions**
* 3a. Client list is empty.  
  Use case ends.

* 5a. User selects an invalid booking index.
    * 5a1. System shows an error message.  
      Use case resumes from step 3.

* 5b. Updated booking time clashes with another booking.
    * 5b1. System displays error indicating time clash.
      Use case resumes from step 5.


**Use Case: Delete a Client**

**Purpose:** Allow the user to permanently delete a client’s profile.

**MSS**
1. User requests to list clients.
2. System displays a list of clients.
3. User selects a client to delete.
4. System deletes the client’s profile.

   Use case ends.

**Extensions**
* 2a. Client list is empty.  
  Use case ends.

* 3a. User selects an invalid client index.
    * 3a1. System shows an error message.  
      Use case resumes from step 3.

* 3b. User has undeleted bookings.
    * 3b1. System indicates client has undeleted bookings.
      Use case resumes from step 3.


**Use Case: View Client Details**

**Purpose:** Allow the user to view all stored information and related bookings for a specific client.

**MSS**
1. User requests to list clients.
2. System displays the list of clients.
3. User selects a client to view.
4. System displays the selected client’s details and all associated bookings. 

    Use case ends.

**Extensions:**
* 2a. Client list is empty.
      Use case ends.

* 3a. User selects an invalid client index.
    * 3a1. System shows an error message.
      Use case resumes from step 23


### Non-Functional Requirements

1. **Platform Independence**
    - The software must run on Windows, Linux, and macOS, and must not use any OS-dependent libraries or features.

2. **Java Version Compatibility**
    - The application must work on a computer that has only Java 17 installed (i.e., it must not require any other Java version).

3. **Portability**
    - The software must be usable without requiring an installer; users should be able to run the JAR file directly.

4. **Single-User Design**
    - The product must be designed for a single user and must not support multi-user access or concurrent data file usage.

5. **Typing-Optimized Interface**
    - The user interface must be optimized for users who type fast and prefer typing over other input methods.

6. **Human-Editable File Format**
    - All data must be stored locally in a human-editable text file format.

7. **No DBMS Usage**
    - The application must not use a database management system (DBMS) such as MySQL to store data.

8. **Object-Oriented Design**
    - The software must primarily follow the object-oriented programming design.

9. **No Remote Server Dependency**
    - The software must not depend on any remote server for its core functionality.

10. **Third-Party Libraries**
    - Any third-party libraries used must be free, open-source, have permissive licenses, and must not require installation by the user.

11. **Screen Resolution Support**
    - The GUI must work well at 1920x1080 resolution and higher (at 100% and 125% scaling), and be usable at 1280x720 and higher (at 150% scaling).

12. **Single-File Distribution**
    - The application and all dependencies must be packaged into a single JAR file (or a single ZIP file if necessary).

13. **File Size Limitations**
    - The JAR/ZIP file size must not exceed 100MB; PDF documentation files must not exceed 15MB each.

14. **Performance**
    - The application must load the client and booking list within 2 seconds for up to 1,000 clients, and must remain responsive during common operations.

15. **Reliability**
    - The application should not crash during normal operations and must recover gracefully from unexpected errors.

16. **Usability**
    - The system should be easy to use for creative professionals with minimal training (<30 minutes).


### Glossary

* **Client**: A person or business that books photography or videography services.
* **Booking**: A scheduled service or session requested by a client.
* **Index**: A numeric position of a client or booking in a displayed list, used for commands.
* **Status**: The stage of client engagement, see [Client Statuses](UserGuide.md#1-client-statuses)
* **Package**: A predefined set of services offered to a client, see [Package Types](UserGuide.md#2-package-types).
* **Tag**: A label that can be attached to a booking or client to help with categorization, see [Tag Keywords](UserGuide.md#3-tag-keywords)
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Gradle**: The build automation tool used to manage dependencies, run builds, and execute tests.
* **JavaFX**: A Java library for building graphical user interfaces (used for InSight’s UI).
* **Human-Editable File**: A file format that users can open and modify without specialized software.
--------------------------------------------------------------------------------------------------------------------

# Appendix: Instructions for Manual Testing

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.
</div>

---

## Launch and Shutdown

### Initial launch

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `insight.jar` file from [here](https://github.com/AY2526S1-CS2103T-T08-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for InSight.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar insight.jar` command to run the application.<br>
   Expected: Shows the GUI with a set of sample contacts and bookings. The window size may not be optimum.

### Saving window preferences

1. Resize the window to an optimum size. Move the window to a different location. Close the window.

2. Re-launch the app by running `java -jar insight.jar` again.<br>
   Expected: The most recent window size and location is retained.

---

## Client Management

### Adding a client

1. Adding a client with valid inputs 
   1. Test case: `add n/Alice Tan p/98765432 e/alice@example.com s/PROSPECT`<br>
      Expected: New client "Alice Tan" is added to the list. Details shown in the status message.

2. Adding a client with invalid inputs
   1. Test case: `add n/Charlie p/12345678 e/charlie@test.com s/INVALID`<br>
      Expected: No client is added. Error message indicates status constraints. Valid statuses are shown.

3. Adding a client - missing required parameters
   1. Test case: `add n/Test`<br>
      Expected: No client is added. Error message shows the correct format with all required parameters.
   2. Other incorrect commands to try: `add p/12345678`, `add` (missing all parameters)<br>
      Expected: Similar error messages showing required format.

### Listing all clients

1. Test case: `list`<br>
   Expected: All clients in InSight are displayed. Client count shown in status message.

### Editing a client

Prerequisites: At least one client displayed. List all clients using the `list` command. 

1. Editing a client with valid inputs.
   1. Test case: `edit 1 p/87654321`<br>
      Expected: First client's phone is updated. Details shown in the status message.
   2. Test case: `edit 1 p/87654321 e/newemail@example.com s/ACTIVE`<br>
      Expected: First client's phone, email, and status are updated. Success message displayed.

2. Editing a client with invalid inputs.
   1. Test case: `edit 100000 n/James` (assuming list has less than 100000 clients)<br>
      Expected: No client is edited. Error message indicates index is invalid.


### Finding clients

Prerequisites: Have clients with various names in the list.

1. Finding clients by name
   1. Test case: `find name alice bob`<`br>
      Expected: Lists all clients whose names contain "alice" or "bob" (case-insensitive). Number of matches shown.

2. Finding clients by status
   1. Test case: `find status active returning`<br>
         Expected: Lists all clients with status ACTIVE or RETURNING. Number of matches shown.

3. Finding clients with invalid inputs.
   1. Test case: `find name`<br>
      Expected: No search performed. Error message shows correct format requiring at least one keyword.
   2. Test case: `find`<br>
      Expected: Error message shows correct format with examples.

### Sorting clients alphabetically

Prerequisites: Have at least 3 clients with different names in the list (e.g., "Charlie", "Alice", "Bob").

1. Test case: `sort`<br>
   Expected: All clients are sorted alphabetically by name (Alice, Bob, Charlie). Status message confirms sorting.

### Deleting a client

Prerequisites: Have at least one client displayed. List all clients using the `list` command.

1. Deleting a client from the full list.
    1. Test case: `delete 1`<br>
       Expected: First client is deleted from the list. Details of the deleted client are shown in the status message. All associated bookings are also deleted.

2. Deleting a client with invalid index.
    1. Test case: `delete 0`<br>
       Expected: No client is deleted. Error message indicates index must be positive.
    2. Test case: `delete 100000` (assuming 100000 is larger than list size)<br>
       Expected: Error message indicates index is out of range.

---

## Booking Management

### Adding a booking

Prerequisites: Have at least one client displayed. List all clients using the `list` command.

1. Adding a booking with valid inputs.
    1. Test case: `addbooking 1 d/Wedding Shoot dt/14/10/2025 1200 p/PORTRAIT t/outdoor t/summer`<br>
       Expected: Booking added to the first client. Details shown in status message including booking index.

2. Adding a booking with invalid inputs.
    1. Test case: `addbooking 1 d/Test dt/32/01/2025 1200 p/PORTRAIT`<br>
       Expected: No booking is added. Error message indicates invalid date (day 32 does not exist).
    2. Test case: `addbooking 100000 d/Test dt/14/10/2025 1200 p/PORTRAIT` (assuming 100000 is larger than list size)<br>
       Expected: No booking is added. Error message indicates invalid client index.
    3. Test case: `addbooking 1`<br>
       Expected: No booking is added. Error message shows correct format with all required parameters.

### Listing all bookings

1. Test case: `listbooking`<br>
   Expected: All bookings in InSight are displayed.

### Viewing bookings for a client

Prerequisites: Have at least one client displayed. List all clients using the `list` command.

1. Viewing bookings for a client.
    1. Test case: `viewbooking 1`<br>
       Expected: Other clients hidden and all bookings for the first client are displayed. Number of bookings shown in status message.

2. Viewing bookings with invalid client index.
    1. Test case: `viewbooking 0`<br>
       Expected: Error message indicates invalid command format.
    2. Test case: `viewbooking 100000` (assuming 100000 is larger than list size)<br>
       Expected: Error message indicates index is out of range.

### Editing a booking

Prerequisites: Have at least one booking displayed. List all bookings using the `listbooking` command.

1. Editing a booking with valid fields
    1. Test case: `editbooking 1 d/Updated Description`<br>
       Expected: First booking's description is updated. Details shown in status message.
    2. Test case: `editbooking 1 d/Updated Description p/WEDDING dt/15/11/2025 1400`<br>
       Expected: First booking's description, package, and datetime are updated. Success message displayed.

2. Editing a booking with invalid fields.
    1. Test case: `editbooking 1 dt/31/02/2025 1200`<br>
       Expected: No booking is edited. Error message indicates invalid date (February 31 does not exist).
    2. Test case: `editbooking 0 d/Test`<br>
       Expected: No booking is edited. Error message indicates command format.

### Marking/Unmarking a booking

Prerequisites: Have at least one booking displayed. List all bookings using the `listbooking` command.

1. Marking a booking as paid.
    1. Test case: `markbooking 1`<br>
       Expected: First booking marked as "Paid". Status shows [X] in the booking list. Success message displayed.

2. Unmarking a booking as not paid.
    1. Test case: `unmarkbooking 1`<br>
       Expected: First booking unmarked as "Not Paid". Status shows [ ] in the booking list. Success message displayed.

3. Marking/Unmarking a booking with invalid index.
    1. Test case: `markbooking 0`<br>
       Expected: Error message indicates invalid command format.
    2. Test case: `unmarkbooking 100000` (assuming 100000 is larger than list size)<br>
       Expected: Error message indicates index is invalid.

### Sorting bookings by date and time

Prerequisites: Have at least 3 bookings with different dates and times.

1. Test case: `sortbooking`<br>
   Expected: All bookings sorted by date and time in chronological order from today (earliest first). Past bookings are not shown. Status message confirms sorting.

### Deleting a booking

Prerequisites: Have at least one booking displayed. List all bookings using the `listbooking` command.

1. Deleting a booking from the full list.
    1. Test case: `deletebooking 1`<br>
       Expected: First booking is deleted. Details of the deleted booking shown in status message.

2. Deleting a booking with invalid index.
    1. Test case: `deletebooking 0`<br>
       Expected: No booking is deleted. Error message indicates invalid command format.
    2. Test case: `deletebooking 100000` (assuming 100000 is larger than list size)<br>
       Expected: Error message indicates index is out of range.
---

## General Commands

### Clearing all data

Prerequisites: Have some clients and bookings in the system.

1. Test case: `clear`<br>
   Expected: All clients and bookings are removed. InSight displays empty lists. Confirmation message shown.

2. Verify by running `list` and `listbooking`<br>
   Expected: Both lists are empty.

### Viewing help window

1. Test case: `help`<br>
   Expected: Help window opens showing command summary and link to user guide.

### Exiting the application

1. Test case: `exit`<br>
   Expected: Application window closes gracefully. All data is saved automatically.

--------------------------------------------------------------------------------------------------------------------
## **Appendix: Effort**

## Appendix: Effort

### Difficulty Level
The overall difficulty level of the project was **moderately high**. While we built upon the existing AddressBook Level 3 (AB3) codebase, our project introduced new entity types (e.g. *clients* and *bookings*), each with distinct attributes, commands, and interactions. Compared to AB3, which manages a single entity type, our system required additional layers of logic to handle interdependencies between clients and their bookings, as well as to maintain data consistency during operations such as edits or deletions.

### Challenges Faced
1. **Heavy Workload and Time Constraints:**  
   The team encountered significant workload balancing challenges, especially when implementing multiple new features concurrently. Integration testing and refactoring took longer than anticipated due to feature interdependencies. Additionally, with a lower team member count, workload was amplified.

2. **Unexpected Bugs:**  
   Several issues arose during command parsing and model synchronization, particularly when implementing features that affected both clients and bookings simultaneously. Debugging these cross-entity interactions required careful tracing and validation to ensure data integrity.

3. **Learning Curve:**  
   Adapting to the existing AB3 architecture and understanding its design patterns (e.g.`Logic`, `Model`, and `Command` layers) posed an initial challenge. The team invested time in learning how to extend these patterns appropriately while maintaining clean architecture and testability.

4. **Data Consistency:**  
   Ensuring that client–booking relationships persisted correctly after operations like delete and edit required additional design considerations. For example, the editing of the unique name of a person had to be updated for all associated bookings.

### Effort and Achievements
Despite the challenges, the team successfully **met all key objectives** outlined at the start of the project:
- Delivered a working product aligned with our **value proposition** — a simple yet powerful CRM tailored for photographers and videographers.
- Implemented all core functionalities including client management, booking tracking, and payment status features.
- Maintained **a consistent and intuitive user experience** by extending AB3’s command-based interface with meaningful feedback messages and clear error handling.
- Ensured **code quality** through continuous testing and adherence to coding standards.

The project demanded significant collaboration and time investment across development, testing, and documentation phases. The average weekly contribution per member was high, particularly during feature integration and testing weeks.

### Reuse of Existing Components
Approximately **10–15% of development effort** was saved through reuse of AB3 components, primarily in command parsing, data storage, and UI structure. For instance, our system reused and extended AB3’s `JsonSerializableAddressBook` and `Command` classes to support new data models.
- The `Booking` feature was implemented by adapting the existing `Person` model.
- The `UniqueBookingList` model was implemented by adapting the existing `UniquePersonList` model

This reuse allowed the team to focus more on designing new features and refining the overall workflow, rather than rebuilding core components from scratch.

### Summary
Overall, the project required a **moderate-to-high level of technical and design effort** due to the addition of multiple interacting entities, new features, and robust data handling. Despite encountering a steep learning curve and several integration challenges, the team delivered a product that not only achieved its goals but also demonstrated effective adaptation of existing architecture to a more complex domain.
