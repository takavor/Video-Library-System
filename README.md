# Video-Player-System-Design
Implementation of a video library system with software development design patterns.

## Design Patterns

Here's an overview of the design patterns used to implement the system.

* **Command**

To implement undo/redo functionality of consuming media objects. Each state-modyfing method has a corresponding Command-creating method which returns an anonymous Command corresponding to the action.

![image](https://user-images.githubusercontent.com/47959146/149541578-e12e1625-a691-4c42-bb63-0a1b2a56ab7f.png)
State transitions of an arbitrary watchlist with respect to state modyfing actions

* **Observer**

To update video properties, such as most recently watched Watchable object. Watchable objects are the models. Notifying methods signal the observers of a change. Implemented using inheritance for system functionality and avoidance of code duplication.

* **Prototype**

To allow the client to create episodes by calling methods in TVShow with polymorphic cloning.

![image](https://user-images.githubusercontent.com/47959146/149542235-9f4b3610-e6f4-4fc4-ac66-025100305162.png)
Sequence diagram for creating a new Episode with the prototype



