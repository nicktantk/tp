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

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

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


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

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

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


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

**Value proposition**:
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
3. User searches for a specific client by name or contact detail.
4. System displays matching client(s).
5. User selects the desired client from the search results.
6. User requests to add a tag to the selected client.
7. System prompts user to enter tag details.
8. User inputs the tag details.
9. System saves the tag under the client’s record.

   Use case ends.

**Extensions**
* 3a. No clients match the search query.
    * 3a1. System shows a “No clients found” message.  
      Use case ends.

* 5a. User selects an invalid client index.
    * 5a1. System shows an error message.  
      Use case resumes at step 2.


**Use Case: Edit Client Booking Details**

**Purpose:** Allow the user to edit details for a client’s existing booking.

**MSS**
1. User requests to list clients.
2. System displays a list of clients.
3. User selects a client from the list.
4. System shows the client’s booking history.
5. User selects a specific booking to edit.
6. System displays the booking details for editing.
7. User updates booking information.
8. System saves the updated booking details.

   Use case ends.

**Extensions**
* 3a. Client list is empty.  
  Use case ends.

* 5a. The chosen booking does not exist.
    * 5a1. System shows an error message.  
      Use case resumes at step 4.


**Use Case: Delete a Client**

**Purpose:** Allow the user to permanently delete a client’s record.

**MSS**
1. User requests to list clients.
2. System displays a list of clients.
3. User selects a client to delete.
4. System prompts for confirmation to delete the client.
5. User confirms deletion.
6. System deletes the client’s record.

   Use case ends.

**Extensions**
* 2a. Client list is empty.  
  Use case ends.

* 3a. User selects an invalid client index.
    * 3a1. System shows an error message.  
      Use case resumes at step 2.


### Non-Functional Requirements

1. `Constraint-Platform_Independent`
- The software must run on Windows, Linux, and macOS, and must not use any OS-dependent libraries or features.

2. `Constraint-Java-Version`
- The application must work on a computer that has only Java 11 installed (i.e., it must not require any other Java version).

3. `Constraint-Portable`
- The software must be usable without requiring an installer; users should be able to run the JAR file directly.

4. `Constraint-Single-User`
- The product must be designed for a single user and must not support multi-user access or concurrent data file usage.

5. `Constraint-Typing-Preferred`
- The user interface must be optimized for users who type fast and prefer typing over other input methods.

6. `Constraint-Human-Editable-File`
- All data must be stored locally in a human-editable text file format.

7. `Constraint-No-DBMS`
- The application must not use a database management system (DBMS) such as MySQL to store data.

8. `Constraint-OO`
- The software must primarily follow the object-oriented programming design.

9. `Constraint-Incremental`
- The product must be developed in a breadth-first, incremental manner, with consistent delivery of working features throughout the project duration.

10. `Constraint-No-Remote-Server`
- The software must not depend on any remote server for its core functionality.

11. `Constraint-External-Software`
- Any third-party libraries used must be free, open-source, have permissive licenses, and must not require installation by the user.

12. `Constraint-Screen-Resolution`
- The GUI must work well at 1920x1080 resolution and higher (at 100% and 125% scaling), and be usable at 1280x720 and higher (at 150% scaling).

13. `Constraint-Single-File`
- The application and all dependencies must be packaged into a single JAR file (or a single ZIP file if necessary).

14. `Constraint-File-Size`
- The JAR/ZIP file size must not exceed 100MB; PDF documentation files must not exceed 15MB each.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Loyalty Tracking**: A feature that records repeat interactions to identify frequent or returning clients.
* **Tag**: A label that can be attached to a booking or client (e.g., “Wedding,” “Corporate,” “Outdoor Shoot”) 
to help with categorization and search.
* **Gradle**: The build automation tool used to manage dependencies, run builds, and execute tests.
* **JavaFX**: A Java library for building graphical user interfaces (used for InSight’s UI).
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
