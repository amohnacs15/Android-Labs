## Android-Labs
Coursera's Mobile Cloud Computing with Android specialization program.
<br>
###Table of Contents:
* [Introduction](#introduction)
* [Comprehensive Labs](#comps)
 * [Modern Art UI](#modernart)
 * [Daily Selfie](#daily)
 * [Material Image Downloader](#downer)
* [Programming Mobile Applications for Handheld Systems: Part 1](#part1)
 * [Activity Lab](#act1)
 * [Intens Lab](#ints)
 * [Permissions Lab](#perms)
 * [Fragments Lab](#frags)
 * [UI Lab](#uilabs)
* [Programming Mobile Applications for Handheld Systems: Part 2](#part2)
 * [AsyncTask Lab](#async)
 * [Notifications Lab](#notes)
 * [Graphics Lab](#graphs)
 * [Location](#locs)
* [Programming Mobile Services for Android Handheld Systems: Concurrency] (#concurrency)
 * [Palantiri Simulation](#p1)
 * [Palantiri Simulation 2](#p2)
  
### <a name="introduction"></a>Introduction
Android apps developed over 21 weeks teaching the core concepts of mobile cloud computing on the Android platform. 
It starts with user-facing applications, then covers middleware, then services running on Android devices, and concludes with integration 
with network-accessible cloud services.

### <a name="comps"></a>Comprhensive Labs
Apps that were developed as the final assignment for the course.

#### <a name="modernart"></a>Modern Art UI
Creates and displays a user interface, presents a menu and a dialog, and that opens a link to an external website. 

#### <a name="daily"></a>Daily Selfie
Reminds the user to periodically take a picture of themselves and allows the user to browse those pictures at a later time. 

#### <a name="downer"></a>Material Image Downloader
Prompts the user for a list of URL to images and then downloads these images, filters the images, and displays them. 
Each download is performed concurrently via different instances of the Android AsyncTask concurrency framework.
All AsyncTasks run concurrently using the AsyncTask.THREAD_POOL_EXECUTOR and the executeOnExecutor() method.
Handle runtime configuration changes properly in the context of concurrently executing instances of AsyncTask.

<br>

### <a name="part1"></a>Programming Mobile Applications for Handheld Systems: Part 1
Android Studio and the Android Development Environment<br>
Screen configurations and sizes<br>
Activity Class, Intents and Permission, Fragments<br>
Designing user interfaces<br>

#### <a name="act1"></a>Activity Lab
Application that monitors itself and reports its behavior by emitting LogCat messages and printing information to the screen. 
Implements the Activity class, the Activity lifecycle, and the Android reconfiguration process.

#### <a name="ints"></a>Intents Lab
Application that creates and uses both explicit and implicit Intents to activate Activities. 
Implements intents to start Activities and transfer data between activities.

#### <a name="perms"></a>Permissions Lab
Application that uses, defines and enforces Android Permissions. 
Implements permissions that are used to control access to particular application components.

#### <a name="frags"></a>Fragments Lab
Application that uses Fragments to produce either a multi-pane or single-pane user interface depending on the current device’s screen size.
Implements the Fragment class and its lifecycle.  Fragments interact with the Activities that host them.

#### <a name="uilabs"></a>UI Labs
Application that creates and displays To Do items. 
Implements Android user interface classes that can be combined and used to create complex and functional user interfaces.

<br>

### <a name="part2"></a>Programming Mobile Applications for Handheld Systems: Part 2
notifying users about important events<br>
handling concurrency<br>
acquiring data over the network <br>
leveraging multimedia and graphics<br>
incorporating touch and gestures<br>
working with sensors<br>

#### <a name="async"></a>AsyncTask Lab
Application that "downloads" and displays tweets. 
Uses AsyncTasks to perform work off the main Thread.

#### <a name="notes"></a>Notifications Lab
Application that downloads and displays tweets. 
Implements BroadcastReceivers that allow components to listen for and react to events, 
and User Notifications that allow apps to inform users about the app's behavior.

#### <a name="graphs"></a>Graphics Lab
Application that displays and manipulates graphic images based on user touch input. 
Implements drawn graphics, touch and gesture handling and play sound effects.

#### <a name="locs"></a>Location Lab
Application that acquires location information, then retrieves data about the current location from an Internet web service, 
and then displays that information in a ListView. 
Uses location information within an Android app.

<br>

### <a name="concurrency"></a>Programming Mobile Services for Android Handheld Systems: Concurrency
Synchronous and asynchronous concurrency models<br> 
Background service processing<br>
Structured data management<br>
Local inter-process communication and networking<br> 
Integration with cloud-based services  

#### <a name="p1"></a>Palantiri Simulation
Android app containing a resource manager that restricts the number of Beings from Middle-Earth who can concurrently gaze into a fixed number of Palantiri.
Uses Java Thread, Runnable, and Semaphore classes, as well as Java synchronized statements.

#### <a name="p2"></a>Palantiri Simulation 2
An extension of Palantiri Simulation with the the added implementation of AsyncTasks and ConcurrentHashMaps.


