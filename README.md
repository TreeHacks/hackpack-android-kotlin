# Android HackPack Tutorial

### Overview
For the Android Hackpack, we are going to develop **HackerPad** - A Simple Note taking application. In this tutorial, you'll learn basic process behind making android apps such as developing an UI, storing data locally, opening activity from another activity and much more. 
So Let's get started!

### Create an Android Project
* If you don't have [Android Project](https://developer.android.com/studio/) installed on your computer, install it from [here](https://developer.android.com/studio/).

* In the **Welcome to Android Studio** window, click **Start a new Android Studio project**.
  ![Android Studio](/docs_assets/as.png)
  Or if you have a project opened, select **File > New Project**.

* In the **Create New Project** window, enter the following values.
  * **Application Name** : "HackerPad"
  * **Company Domain** : "your_name.io"
  Leave the other options as they are.

* For all the other setup screens **select default values** and hit next.

After some processing, Android Studio opens the IDE.

![](/docs_assets/fs.png)

Now let's take a moment to review the most important files.

First, be sure the **Project window is open (select View > Tool Windows > Project)** and the Android view is selected from the drop-down list at the top of that window. You can then see the following files:

**app > java > io.your_name.hackerpad > MainActivity**
This is the main activity (the entry point for your app). When you build and run the app, the system launches an instance of this Activity and loads its layout.

**app > res > layout > activity_main.xml**
This XML file defines the layout for the activity's UI. It contains a TextView element with the text "Hello world!".

**app > manifests > AndroidManifest.xml**
The manifest file describes the fundamental characteristics of the app and defines each of its components.

**Gradle Scripts > build.gradle**
You'll see two files with this name: one for the project and one for the "app" module. Each module has its own build.gradle file, but this project currently has just one module. You'll mostly work with the module's build.gradle file to configure how the Gradle tools compile and build your app. 

### Run the "Hello World" app
Press the run button (circled in following figure) and you will see a hello world written in middle of screen. You will need to add a emulator/device to run the app. Just follow the default steps to add emulator or plug your android device using usb.
![](/docs_assets/run.png)

## Now Let's Start Building our Note Taking App - *HackerPad*

Let's define our app design. It will be two activity application - 

- **MainActivity**: This will show the list of all the notes already added and also will include a button to add a new note. Finally, it would look like this - 

  ![](/docs_assets/mnaT.png) 

- **NotesActivity**: This will be the details activity where title and details of note can be added. Finally, it would look like this - 

  ![](/docs_assets/ntact.png)

### Lets build our User Interface (UI)

UI in android is build by using Design editor (Preferred way) or by using text editor. All the UI in android is declared using XML and can be found in **app > res > layout**.

Edit **app > res > layout > activity_main.xml ** as follows and let's see what we are doing

```html
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.MainActivity">

    <ListView
        android:id="@+id/noteListView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </ListView>

    <android.support.v7.widget.CardView xmlns:card_view="http://schemas.android.com/apk/res-auto"
        android:id="@+id/card_view"
        android:layout_width="match_parent"
        android:layout_height="@dimen/cardview_height"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        card_view:cardCornerRadius="4dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.982">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/takeNote"
                android:layout_width="269dp"
                android:layout_height="44dp"
                android:layout_margin="2dp"
                android:layout_weight="3"
                android:gravity="center_vertical|start"
                android:padding="2dp"
                android:text="@string/add_note_placeholder"
                android:textColor="@color/gray"
                android:textSize="14sp" />

            <ImageView
                android:id="@+id/takeAudioNoteView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="2dp"
                android:layout_weight="1"
                android:adjustViewBounds="true"
                android:contentDescription="@string/audio_note_placeholder"
                android:cropToPadding="false"
                android:tint="@color/colorPrimary"
                android:padding="2dp"
                android:scaleType="center"
                android:visibility="invisible"
                app:srcCompat="@drawable/ic_action_name" />
        </LinearLayout>
    </android.support.v7.widget.CardView>
</android.support.constraint.ConstraintLayout>
```

In this layout, we have added a **\<ListView> tag** which is a view that can show list of elements in scrollable fashion. (Note that presently Google recommend **\<RecyclerView>** for showing scrollable list, but for simplicity sake, we are using ListView and also it fulfills our purpose easily.) We will use ListView to show list of already added notes and we have given it **id as noteListView** so as to get its reference later.

Also there is **card view widget** added which includes a **Linear Layout** which layouts its children in linear fashion. This part of code builds our **Take a Note** action button along with **Speech icon**.  All these codes are generated using design editor which is a simple intuitve drag and drop editor for building UI.

Similary, for notes activity, create a new activity by **File > New > Activity > Empty Activity** , name the activity to NotesAcitivity and selecting all the other default values. This will generate two files:-

- **NotesActivity.java** in app > java > io.your_name.hackerpad 
- **activity_notes** in app > res > layout

Edit activity_notes with this repository's activity_notes file. Its a simple layout file with two field to take user inputs as title & note along with a button to add/update the note.

### Let's Build our Model 

Model refers to the Plain objects needed for the application which in our case is clearly a Note.
So, we will define a Note class with following fields:

- Id - A string feild which will be the identifier for a particular note
- Title - A string feild which will be the title for our note
- message - A string feild which will be the message for our note
- isPinned - A boolean feild which will show if a note is pinned or not.
- isBookmarked - A boolean feild which will show if a note is bookmarked or not.

Create a new **data** directory in **app > java > packageName**. In this directoy we will create two more directory, **model** which will contain our plain object and **repository** which will handle our data storage. 
Copy the [Note.java](https://github.com/navi25/hackpack-android/blob/master/app/src/main/java/com/treehacks/hackpack_android/data/model/Note.java) file present in this repository into your project. 

### Now lets define our Data handling

We will be following Reposiotry pattern to handle our data which is best explained by the following diagram:

![](/docs_assets/repoPat.png)

Now let's create a interface first to define our Data handling functions. Create **INotesRepository** in the repository directory created in data folder and define it as follows:

```java
public interface INotesRepository {
    List<Note> getNotesList();
    void add(Note note);
    void update(Note note);
    Note getNoteById(String id);
    boolean deleteNoteById(String id);
}
```

As clear by the function signatures, this interface defines all the necessary operations we want to do with our data like Adding a new note, Updating it, Retreving it by Id, Deleting it and also Getting all notes.

Lets implement these in a **LocalNotesRepository** class.

Copy LocalNotesRepository.java file to your **repository** directory. Remember to change the package name. 
The following code is self explanatory, we have a arraylist of Note and we are using it for doing our CRUD operations.

```java

 	private List<Note> notesList = new ArrayList<>();

    public void add(Note note){
        String id = UUID.randomUUID().toString();
        note.setId(id);
        notesList.add(note);
    }

    public Note getNoteById(String id){
        for (Note note:notesList) {
            if(note.getId().equals(id)){
                return note;
            }
        }

        return null;
    }

    public void update(Note newNote) {
        for (Note note:notesList) {
            if(note.getId().equals(newNote.getId())){
                note.update(newNote.getTitle(), newNote.getNote());
            }
        }
    }


    public boolean deleteNoteById(String id){
        for (Note note:notesList) {
            if(note.getId().equals(id)){
                notesList.remove(note);
                return true; //Success | Note with given id successfully deleted
            }
        }
        return false; // Failure | Note with given id not found
    }
```

### Local Storage

For Storage we will use Android **Shared Preferences** which is a small collection of Key-Value pair available to the apps and is good as storage for some simple prototyping usecases. Otherwise generally we need to access Sqlite3 database on android which comes inbuilt with every android. But for simplicity we will be using this.

We need to get access to this object from Android and we will do it in **App** file which will be entry point for our application where we would define all our top level objects. 

Copy **App** class present in **app>Java>packageName** to your **app>Java>packageName** 

```java
public class App extends Application {

    //...
    
      public String getPrefKey() { return prefKey; }
    
      public SharedPreferences getSharedPrefs(){
        return this.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE);
      }

    public INotesRepository getNotesRepository(){
        if(testingMode){
            return DummyNotesRepository.getInstance(this);
        }
        return LocalNotesRepository.getInstance(this);
    }

    public void saveNotes(){
        List<Note> noteList = getNotesRepository().getNotesList();
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        getSharedPrefs().edit()
                .putString(prefKey,json)
                .apply();
    }
    
}
```

As shown in above code, we have getter for our repositories and SharedPreferences as required and **saveNotes()** method is called when we want to store our notes on local storage. Also, since SharedPreferences can save standard objects, so for our custom Notes object we are using GSON serailiser to convert it to string and storing it as string. By, using GSON we can also easily create our Note Object back from a string.

### Integrating the app

Now its time to integrate various components and make it work. For that create a activity file using **File > New > Activity > Blank Activity** and call it NotesActivity. So now we have two Activity file - Main and Notes.

Now lets see what we are doing there. This is MainActivity. For brevity, I am showing only necessary codes.

```java
 	//MainActivity.java
    private ListView noteListView;

    private INotesRepository notesRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up references for the views present in activity_main layout file
        noteListView = findViewById(R.id.noteListView);
        TextView takeNoteView = findViewById(R.id.takeNote);
        //You can use below AudioNoteView to add audio note functionality to the app
        //ImageView takeAudioNoteView = findViewById(R.id.takeAudioNoteView);

        //Getting reference of NotesRepository from App class
        notesRepo = ((App)getApplication()).getNotesRepository();

        /**
        * Setting up OnClickListener to handle onClick event for TakeNoteView
        * It start NotesActivity on onClick event.
        **/
        takeNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTakeNoteActivity();
            }
        });

        // Populate the current listview with notes.
        populateNotes();
    }
```

Here we have defined variables to access our previously created noteListView and created a reference for notesRepo and then in onCreate() method which is a activity lifecycle callback. we are setting up the references appropriately.

```java
	//This is the function to start a new activity from current main activity
	private void startTakeNoteActivity(){
        //This is the intent class which is a special object to start a new action in Android
        Intent intent = new Intent(this, NotesActivity.class);
        //This is a in built function that starts a new activity as per the intent specs
        startActivity(intent);
    }

	//This is overloaded function to open note activity. This will be used for updating notes
    private void startTakeNoteActivity(Note note){
        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("noteId",note.getId());
        startActivity(intent);
    }

	//This is function to show current notes in a list view
    private void populateNotes(){
		
        //Get reference to the Note List from notes repository
        final List<Note> noteList = notesRepo.getNotesList();

        /**
        * This is the adapter that creates the passed view (R.layout.note_card_view)
        * and populate a Text view present in the view with the passed data objects
        **/
        final ArrayAdapter<Note> noteArrayAdapter = new ArrayAdapter<Note>(
                this, R.layout.note_card_view, R.id.note, noteList);

        
        noteArrayAdapter.notifyDataSetChanged();

        //Some decoration functions for decorating our Noteview
        noteListView.setAdapter(noteArrayAdapter);
        noteListView.setPadding(8,8,8,8);
        noteListView.setDividerHeight(8);

        //Setting Click listener for individual Note Item
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
               	//Get Note object present at that position
                Note note = (Note)parent.getAdapter().getItem(position);
                //Start Note Activity to update it 
                startTakeNoteActivity(note);
            }
        });
    }

```

Now lets check how we are implementing NotesActitivty.java

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        
        //Get Reference to the notes repository
        notesRepo = ((App)getApplication()).getNotesRepository();

        //Set references to TextView defined in activity_notes layout file
        titleView = findViewById(R.id.noteTitle);
        noteView = findViewById(R.id.note);

        Button imageButton = findViewById(R.id.imageButton);
        imageButton.setText(R.string.add_note);

       //Set on Click Listener for ImageButton click which is Add button
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add Note and return to the main activity
                addNote();
                startMainActivity();
                return;
            }
        });

        /**
        * To bring focus to note view. This brings the functionlity of writing as soon as 
        * this activity is launched.
        **/
        noteView.requestFocus();

    }


```



## Hit Run

Now hit run and you can see a working prototype of your Note Taking application - **HackerPad**

## What Next

There are lot of things that you can do on top of this application.

1. [Google Search API](https://developers.google.com/custom-search/) : Use Google Search API to bring search fucntionalties to your app.
2. [Google Speech API](https://cloud.google.com/speech-to-text/docs/) : Use Google Speech API to take audio notes or translate a given speech into English Text.
3. [Google Voice API](https://developers.google.com/voice-actions/system/) : Use Google Voice API to add custom functionalities on "Ok Google" Command.
4. [Google APIs for Android](https://developers.google.com/android/) : A one-stop collection for all the google apis documentation and codelabs for Android.

### More Resources

Android development can be overwhelming in the beginning but there are lots of learning resources available on internet. Feel free to go through them:

- [Official Android Documenation](https://developer.android.com/guide/): - One of the best
- [Android Fundamentals Training by Google Codelab](https://developer.android.com/courses/fundamentals-training/toc-v2) - A full list of code lab style tutorial by Google for teaching android from fundamentals to advanced. (Highly recommended!)
- [Sunshine Weather App - Google CodeLab](https://codelabs.developers.google.com/codelabs/build-app-with-arch-components/index.html?index=..%2F..index#0) - A good end to end app following recommended architecture pattern.

**_Happy Hacking!_**





