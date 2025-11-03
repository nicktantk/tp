---
  layout: default.md
  title: "InSight User Guide"
  pageNav: 3
---

# InSight User Guide

> **Version:** 1.0  
> **Built for:** Creative professionals (photographers, videographers, and media freelancers)  
> **Based on:** SE-EDU AddressBook Level 3

---

## 📚 Introduction

InSight is a lightweight Client & Booking manager designed for small **photography/videography** teams that sell
creative services directly to clients and businesses.

If you’ve ever juggled details across **email, WhatsApp,
spreadsheets, and sticky notes**, you know how easy it is to miss a change request, forget a payment update, or lose
track of which package a client actually chose.

InSight consolidates your **people, bookings, packages, notes, and tags** into one clean workspace.


> **Who this is for:** Solo creators, small studios, and boutique teams in the **photography/videography market**
> looking for speed, clarity, and control—without paying with time or sanity.


![Ui.png](images/Ui.png)
## 📖 Table of Contents
1. [Quick Start](#-quick-start)
2. [Command Reference](#-command-reference)
3. [Features](#-features)
    - [Adding a Client: `add`](#adding-a-client-add)
    - [Adding a Booking: `addbooking`](#adding-a-booking-addbooking)
    - [Listing all Clients: `list`](#listing-all-clients-list)
    - [Listing all Bookings: `listbooking`](#listing-all-bookings-listbooking)
    - [Marking a Booking as Paid: `markbooking`](#marking-a-booking-as-paid-markbooking)
    - [Marking a Booking as Not Paid: `unmarkbooking`](#marking-a-booking-as-unpaid-unmarkbooking)
    - [Editing a Client: `edit`](#editing-a-client-edit)
    - [Editing a Booking: `editbooking`](#editing-a-booking-editbooking)
    - [Deleting a Client: `delete`](#deleting-a-client-delete)
    - [Deleting a Booking: `deletebooking`](#deleting-a-booking-deletebooking)
    - [Finding Clients: `find`](#finding-clients-find)
    - [View all a Client’s Bookings: `viewbooking`](#view-all-a-clients-bookings-viewbooking)
    - [Sorting Clients: `sort`](#sorting-clients-sort)
    - [Sorting Bookings: `sortbooking`](#sorting-bookings-sortbooking)
    - [Clearing All Data: `clear`](#clearing-all-data-clear)
    - [Help: `help`](#viewing-help-help)
    - [Exit: `exit`](#exiting-the-program-exit)
4. [Glossary](#-glossary)
5. [Saving the Data](#-saving-the-data)
6. [Frequently asked questions](#-frequently-asked-questions-faq)
7. [Known Issues](#-known-issues)

---

## ⚡ Quick-Start

1. Ensure **Java 17** or above is installed on your computer.
2. Download the latest `.jar` file from [InSight Releases](https://github.com/se-edu/addressbook-level3/releases).
3. Copy the file to your preferred folder.
4. Open a terminal and run:
   ```bash
   java -jar insight.jar
   ```
5. Use commands such as `help`, `list`, or `add` to begin.

> 💡 **Tip:** Refer to the [Features](#-features) section for detailed examples of each command.

---

## 💻 Command Reference

### Client Management Commands

| Command         | Description                                                                                                                                                                                     |
|-----------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `add`           | Adds a client to InSight. <br/> Parameters: `n/NAME p/PHONE e/EMAIL s/STATUS [a/ADDRESS] [t/TAG]` <br/> Example: `add n/John Doe p/98765432 e/johnd@example.com s/PROSPECT a/311, Clementi Ave` |
| `delete`        | Deletes a client. <br/> Parameters: `INDEX`  <br/> Example: `delete 1`                                                                                                                          |
| `edit`          | Edits client details. <br/> Parameters: `INDEX [n/NAME] [p/PHONE] [e/EMAIL] [s/STATUS] [a/ADDRESS] [t/TAG]` <br/> Example: `edit 1 p/91234567 e/johndoe@example.com`                            |
| `list`          | Lists all clients. <br/> Example: `list`                                                                                                                                                        |
| `find`          | Finds clients by name or status. <br/> Parameters: `name + [name1 name2…]` or `status + [status1 status2…].  <br/> Examples . `find name alice bob . `find status active returning`             |
| `sort`          | Sorts clients lexicographically.   <br/> Example: `sort`                                                                                                                                        |

### Booking Management Commands
| Command         | Description                                                                                                                                                                                          |
|-----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `addbooking`    | Adds a booking to InSight. <br/> Parameters: `INDEX d/DESCRIPTION dt/dd/mm/yyyy HHmm p/PACKAGE t/TAG` <br/> Example: `addbooking 1 d/Wedding Shoot dt/14/10/2025 1200 p/PORTRAIT t/outdoor t/summer` |
| `editbooking`   | Edits booking details. <br/> Parameters: `INDEX [d/DESCRIPTION] [dt/DATETIME] [p/PACKAGE] [t/TAG]`  <br/> Example: `editbooking 1 d/Wedding Shoot p/WEDDING`                                         |
| `deletebooking` | Deletes a booking. <br/> Parameters: `INDEX` <br/> Example: `deletebooking 1`                                                                                                                        |
| `listbooking`   | Lists all bookings <br/> Lists all bookings within InSight. <br/> Example: `listbooking`                                                                                                             |
| `viewbooking`   | Displays all bookings for a specific client.<br/> Parameters: `INDEX` <br/> Example: `viewbooking 1`                                                                                                 |
| `sortbooking`   | Sorts bookings by date and time.   <br/> Example: `sortbooking`                                                                                                                                      |
| `markbooking`   | Marks a booking as 'Paid'. <br/> Parameters: `INDEX` <br/> Example: `markbooking 1`                                                                                                                  |
| `unmarkbooking` | Unmarks a booking as 'Not Paid'. <br/> Parameters: `INDEX`   <br/> Example: `unmarkbooking 1`                                                                                                        |

### General Commands
| Command | Description                                          |
|---------|------------------------------------------------------|
| `clear` | Clears all data from InSight. <br/> Example: `clear` |
| `help`  | Displays help information. <br/> Example: `help`     |
| `exit`  | Exits InSight. <br/>  Example: `exit`                |

---

## 🧭 Features
- See the [Glossary](#-glossary) if a term is unfamiliar.
- Valid client **status** values are listed [here](#1-client-status).
- Read about **INDEX** behavior [here](#5-indexing).
- Date & time formatting is defined [here](#4-date--time-format).
- Suggested **package types** are listed [here](#2-package-types).
- Learn how to use **tags** for your own tracking [here](#3-tag).

### Adding a Client: add

Adds a Client to InSight.

**Format:**  
`add n/NAME p/PHONE e/EMAIL s/STATUS [a/ADDRESS] [t/TAG]…`

💡 **Tip:** A client can have any number of tags (including 0).

The status field (e.g., PROSPECT, ACTIVE, RETURNING) helps classify clients by engagement level.

**Examples:**  
`add n/John Doe p/98765432 e/johnd@example.com s/PROSPECT a/311, Clementi Ave t/wedding`  
`add n/Betsy Crowe t/friend e/betsycrowe@example.com s/ACTIVE a/Newgate Prison p/1234567 t/portrait`
![add.png](images/add.png)

### Adding a booking: addbooking

Adds a booking to a specific client in InSight.

**Format:**  
`addbooking INDEX d/DESCRIPTION dt/dd/mm/yyyy HHmm p/PACKAGE [t/TAG]…`

**Notes:**
- The INDEX refers to the client’s index in the displayed list.
- Each booking is tied to a client entry.
- Tags can represent the booking type, theme, or location.

**Examples:**  
`addbooking 1 d/Wedding Shoot dt/14/10/2025 1200 p/PORTRAIT t/outdoor t/summer`  
`addbooking 2 d/Product Photoshoot dt/10/09/2025 1600 p/CORPORATE t/studio`

![addBooking.png](images/addBooking.png)

### Listing all clients: list

Shows a list of all clients stored in InSight.

**Format:**  
`list`

**Example:**  
`list`

### Listing all bookings: listbooking

Displays all bookings stored in InSight.

**Format:**  
`listbooking`

**Example:**  
`listbooking`

### Marking a booking as paid: markbooking

Marks a booking as Paid.

**Format:**  
`markbooking INDEX`

**Notes:**  
The INDEX refers to the booking number displayed in the booking list. This helps users track completed or settled payments.

**Example:**  
`markbooking 1`
![markbooking.png](images/markBooking.png)

### Marking a booking as unpaid: unmarkbooking

Marks a booking as Not Paid.

**Format:**  
`unmarkbooking INDEX`

**Example:**  
`unmarkbooking 2`
![unmarkbooking.png](images/markBooking.png)

### Editing a client: edit

Edits details of a client in InSight.

**Format:**  
`edit INDEX n/NAME p/PHONE e/EMAIL s/STATUS [a/ADDRESS] [t/TAG]…`

**Notes:**
- The INDEX refers to the client’s position in the displayed list.
- At least one optional field must be provided.
- Editing tags will overwrite previous tags.
- To remove all tags, type `t/` without specifying any tag.

**Examples:**  
`edit 1 p/91234567 e/johndoe@example.com`  
`edit 2 n/Betsy Crower s/RETURNING t/`
![editPerson.png](images/editPerson.png)

### Editing a booking: editbooking

Edits details of an existing booking.

**Format:**  
`editbooking INDEX d/DESCRIPTION dt/DATETIME p/PACKAGE [t/TAG]…`

**Notes:**
- The INDEX refers to the booking index in the booking list.
- You can update multiple fields at once.
- To clear all tags, type `t/` without specifying any.

**Examples:**  
`editbooking 1 d/Wedding Shoot p/WEDDING`  
`editbooking 2 dt/21/09/2025 1600 t/sunset`

### Deleting a client: delete

Deletes a client from the InSight database.

**Format:**  
`delete INDEX`

**Notes:**
- Deletes the client at the specified index from the client list.
- All related bookings remain in the system unless deleted manually.

**Examples:**  
`list` followed by `delete 2` deletes the second client in the list.  
`find Betsy` followed by `delete 1` deletes the first client in the search results.

### Deleting a booking: deletebooking

Deletes a booking record from InSight.

**Format:**  
`deletebooking INDEX`

**Example:**  
`deletebooking 1`

### Finding clients: find

Finds clients whose names or statuses match the given keywords.

> **Note**: Type in the exact name or status to find the clients

**Format:**  
`find name KEYWORD [MORE_KEYWORDS]`  
or  
`find status STATUS [MORE_STATUSES]`

**Notes:**
- Search is case-insensitive.
- The order of keywords does not matter.
- Returns any client matching one or more keywords (OR search).

**Examples:**  
`find name alice bob charlie`  
`find status active returning`
![findStatus.png](images/findStatus.png)

### View all a Client's Bookings: viewbooking

Displays all bookings associated with a selected client.

**Format:**  
`viewbooking INDEX`

**Notes:**  
The INDEX refers to the client’s index. Use this command to view all bookings tied to a specific Client.

**Example:**  
`viewbooking 1`
![viewBooking.png](images/viewBooking.png)

### Sorting clients: sort

Sorts clients alphabetically by name.

**Format:**  
`sort`

**Example:**  
`sort`
![sort.png](images/sort.png)

### Sorting bookings: sortbooking

Sorts all bookings by date and time.

**Format:**  
`sortbooking`

**Example:**  
`sortbooking`

### Clearing all data: clear

Clears all clients and bookings from InSight.

The command clears InSight immediately, there is no confirmation button.

> ⚠️ **Warning:** This permanently deletes **all clients and bookings** from InSight. It **cannot be undone**. Back up your data (copy the `.json` data file) before running `clear`.

**Format:**  
`clear`

**Example:**  
`clear`

### Viewing help: help

Shows usage instructions and available commands.

**Format:**  
`help`

**Example:**  
`help`
![help.png](images/help.png)

### Exiting the program: exit

Closes the InSight application.

**Format:**  
`exit`

**Example:**  
`exit`

---

## 📚 Glossary

### 1. Client Status


| Status    | Meaning                                                                        | Typical Use Case                                       |
|-----------|--------------------------------------------------------------------------------|--------------------------------------------------------|
| PROSPECT  | A potential client who has expressed interest but not confirmed a booking yet. | New lead from social media, email, or inquiry form.    |
| ACTIVE    | A current client with one or more ongoing or upcoming bookings.                | Client with a confirmed event or ongoing project.      |
| RETURNING | A past client who has returned for additional services.                        | Repeat customer booking a new shoot or event.          |
| COMPLETED | A client whose project(s) have been completed and delivered.                   | Finished work, awaiting feedback or testimonial.       |
| INACTIVE  | A client who has not engaged for a long period or has opted out.               | Dormant contacts for archiving or marketing reference. |

### 2. Package Types

| Package Type | Short Description                                                                  |
|--------------|------------------------------------------------------------------------------------|
| PORTRAIT     | Individual or couple portraits, studio or on-location, with light retouching.      |
| FAMILY       | Candid and posed family session; multi-generation friendly, on-location or studio. |
| EVENT        | Coverage of corporate/social events with highlights, details, and guest candids.   |
| WEDDING      | Half- or full-day coverage of preparations, ceremony, portraits, and reception.    |
| CORPORATE    | Professional headshots and office lifestyle imagery for brand and profiles.        |
| PRODUCT      | Clean e-commerce/product images with multiple angles and simple styling.           |
| MATERNITY    | Artistic maternity session focused on mom-to-be; partner optional.                 |
| BABY         | Newborn/infant session at home or studio; safety-first posing and lifestyle.       |
| GRADUATION   | Cap-and-gown portraits and family moments on campus or in studio.                  |
| BIRTHDAY     | Party coverage including cake-cutting, decor, activities, and guest candids.       |
| ANNIVERSARY  | Couple session celebrating milestones; candid and editorial-style portraits.       |
| OTHER        | Custom brief—share your idea and we’ll tailor coverage to fit.                     |
                    |


### 3. Tag
Short, user-defined labels you attach to Clients and Bookings to surface specific details at a glance. 
The app does not enforce meaning — they are up to the user to define as they see fit.
#### Good practices

Keep tags short and consistent (prefer lowercase; use hyphens for multi-word tags).

Use tags for quick flags;

### 4. Date & Time Format

All bookings use the datetime format:  
`dt/dd/mm/yyyy HHmm`

**Example:**  
`dt/14/10/2025 1200` → October 14, 2025, at 12:00 PM.

### 5. Indexing

The INDEX parameter refers to the numeric position of an entry (client or booking) in the currently displayed list.

**Example:**  
`edit 1` edits the first client.  
`editbooking 2` edits the second booking shown in the list.

---

## 💾 Saving the Data

InSight automatically saves all changes to disk after each command. No manual saving is required.


---

## ❔ Frequently Asked Questions (FAQ)

**Q: Where is my data stored?**  
**A:** InSight saves to a JSON file in the `data/` folder (same directory as the app). If the file doesn’t exist, it’s created on first run.  
💡 *Tip:* Keep the whole `data/` folder together when moving machines.

**Q: How do I transfer data to another computer?**  
**A:** Close InSight → copy the entire `data/` folder from the source machine → paste it beside the app on the target machine → start InSight.  
💡 *Tip:* If you only need the core records, copy the `.json` data file inside `data/`.

**Q: How do I back up and restore my data?**  
**A:** Back up by copying `data/` to a safe location. Restore by replacing the current `data/` with your backup copy.  
⚠️ *Note:* Always close InSight before replacing files.

**Q: What if the app says my JSON is corrupted?**  
**A:** InSight will refuse to load malformed JSON. Restore from a backup, or open the file in a text editor and fix the last edited entry.  
💡 *Tip:* Validate the file with any JSON linter before relaunching.

**Q: Can I sync data with cloud services (Drive/Dropbox)?**  
**A:** Yes—sync the *entire* `data/` folder. Avoid opening InSight on two computers at the same time to prevent conflicts.

**Q: Does InSight autosave?**  
**A:** Yes. Changes are written to disk after each successful command. No manual “save” needed.

**Q: How are bookings linked to clients?**  
**A:** Each booking references a client in your People list. Delete/rename clients carefully—bookings depend on them.  
💡 *Tip:* Prefer editing a client over deleting to preserve booking history.

**Q: What do booking statuses mean?**  
**A:** Common statuses include `PENDING`, `CONFIRMED`, `COMPLETED`, `CANCELLED`, `NO_SHOW`. Teams may add others if configured.  
💡 *Tip:* Keep your status set small and consistent for easier filtering.

**Q: How do I find things quickly?**  
**A:** Use `find`/`filter` with fields (e.g., name, tag, status, date). Combine terms to narrow results.

**Q: How do I undo/redo mistakes?**  
**A:** Use `undo` to revert the last valid change and `redo` to re-apply it (where supported).  
⚠️ *Note:* Not all view-only actions are undoable.

**Q: Will updating the app erase my data?**  
**A:** No. Updates don’t touch your `data/` folder. Still, back up before major upgrades.

**Q: What Java version do I need?**  
**A:** Java 17 or later.  
💡 *Tip:* Run `java -version` to check. If you see `1.8/8`, upgrade to 17+.

**Q: Can I keep separate profiles (e.g., work vs personal)?**  
**A:** Yes. Create separate app folders, each with its own `data/`. Launch the one you need.

**Q: Where are logs for troubleshooting?**  
**A:** Check the `logs/` folder next to the app. Attach the latest log when reporting issues.

**Q: How do I reset to a clean slate?**  
**A:** Close InSight → rename or remove the current `data/` folder → relaunch (a fresh data set is generated).  
⚠️ *Note:* This is destructive—back up first.

**Q: Why can’t I delete a client with existing bookings?**  
**A:** To protect data integrity. Delete or reassign those bookings first, then remove the client.

**Q: Can I export my data?**  
**A:** Yes—your live store is already JSON. Copy the main `.json` file for export; you can convert JSON to CSV with external tools if needed.

**Q: How do I report a bug or request a feature?**  
**A:** Include app version, OS, steps to reproduce, and the latest `logs/` file. Clear, minimal steps help us fix it faster.

---

## ⚠️ Known Issues

- Moving the GUI to another monitor may cause positioning errors on reopen.
- If the Help Window is minimized, running `help` again will not reopen it automatically.

---

> © 2025 InSight Team. Built upon SE-EDU AddressBook Level 3.
