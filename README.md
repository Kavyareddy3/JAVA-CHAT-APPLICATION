# Java Chat Application

# Project Overview
  The Java Chat Application is a real-time chat system built using Java, Socket Programming, Threads, and JavaFX.
  It allows multiple clients to connect to a server and communicate through:
  Group chat – All users connected can see messages
  Private chat – Send messages to a specific user using @username
  Message encryption – Basic Caesar cipher encryption for messages
# Objective
  Implement a peer-to-peer chat system using sockets
  Handle multiple clients simultaneously using threads
  Create a user-friendly GUI with JavaFX
  Demonstrate basic encryption in message transmission
# Tools & Technologies
  Language: Java 11+
  GUI: JavaFX 25
  Networking: Socket Programming
  IDE: Any (Eclipse, IntelliJ, or VS Code)
  OS: Windows
# Features
  1.Server-Client Architecture:
     Server handles multiple clients simultaneously
     Clients connect using TCP sockets
  2.Group Chat
     All connected clients receive messages sent to the group
  3.Private Chat
     Use @username message to send messages to a specific user
  4.JavaFX GUI
     Text area displays chat
     Text field for message input
     Send button or press Enter to send messages
  5.Message Encryption
     Simple Caesar cipher shifts characters by 2
     Encrypts messages before sending to server
     Server decrypts messages before broadcasting
