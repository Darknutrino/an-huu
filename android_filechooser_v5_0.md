# Introduction #

Some people are still interested in old version of this library -- `android-filechooser` v5.0. This wiki page helps you work with that version.


# Downloads #

| **Filename** | **Size (~)** |
|:-------------|:-------------|
| [android-filechooser\_v5.0\_src.7z](https://dl.dropboxusercontent.com/u/237978006/android/An%20H%E1%BB%AFu/android-filechooser_v5.0_src.7z) | `276 KiB`    |
| [android-filechooser\_v5.0\_javadocs.zip](https://dl.dropboxusercontent.com/u/237978006/android/An%20H%E1%BB%AFu/android-filechooser_v5.0_javadocs.zip) | `379 KiB`    |


# Integrating into Eclipse IDE #

**First,** you have to open this project in Eclipse. Assuming you place this library at `/data/projects/android-filechooser`.

If you use Ant, to re-generate your `local.properties`, open terminal, use `android` tool (in SDK):
```
# [Android SDK]/tools/android update project -p /data/projects/android-filechooser/code --target android-19
```

**Then, import** this library into your project:

  * By Eclipse: Right click on your project, select _Properties_, select _Android_ tab, then add this library to _Library_ box.
  * Manual: Open your _project.properties_ file, add this line:
```
android.library.reference.1=/data/projects/android-filechooser/code
```
> _Note:_ `1` is the sequence number of the library. Reset it to fit your project. Perhaps it starts from `1`, I don't know  :-D

**Import** `FileChooserActivity` into your application:
  * By Eclipse: Open _AndroidManifest.xml_ -> tab _Application_ -> box _Application Nodes_, click _Add_, select `Activity`. Go to box _Attributes for Activity_, click _Browse_ (next to field _Name_), then add `FileChooserActivity`.
  * Manual: Open _AndroidManifest.xml_, inside tag `application`, add this:
```
<activity
    android:name="group.pals.android.lib.ui.filechooser.FileChooserActivity" />
```

**Android** team recommends that you should set your target SDK to the newest one. You can still use the library for Android 1.6+. For example:
```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="android.dumdum"
    android:versionCode="19"
    android:versionName="2.7" >

    <uses-sdk
        android:minSdkVersion="4"
        android:targetSdkVersion="19" />
    ...
```

  * To avoid the activity from being killed after screen orientation changed, add this:
```
<activity
    android:name="group.pals.android.lib.ui.filechooser.FileChooserActivity"
    android:configChanges="orientation|screenSize|keyboard|keyboardHidden"
    android:screenOrientation="user" />
```

**Import** service `LocalFileProvider` into your application: like `FileChooserActivity`, but this time it is a service:

```
<service
    android:name="group.pals.android.lib.ui.filechooser.services.LocalFileProvider" />
```


# Usage #

## Choose a file ##

```
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;

...

// This is your preferred flag
private static final int REQ_CHOOSE_FILE = 1;

...

Intent intent = new Intent(your-context, FileChooserActivity.class);
/*
 * By default, if not specified, SD card is root path. If SD card is not
 * available, "/" will be used.
 *
 * Note that LocalFile is used instead of File.
 */
intent.putExtra(FileChooserActivity._Rootpath, new LocalFile("/sdcard"));
startActivityForResult(intent, REQ_CHOOSE_FILE);
```

## And to get the result ##

```
@Override
protected void onActivityResult(int requestCode, int resultCode,
        Intent data) {
    switch (requestCode) {
    case REQ_CHOOSE_FILE: {
        if (resultCode == RESULT_OK) {
            /*
             * A list of files always returns. If selection mode is single,
             * then the list contains one file.
             */
            ArrayList<IFile> files = (ArrayList<IFile>) data.getSerializableExtra(
                    FileChooserActivity._Results);
            /*
             * Since we use LocalFileProvider, we can cast the result to File.
             */
            for (IFile _ : files) {
                File f = (File) _;
                ...
            }
        }
        break;
    }// REQ_CHOOSE_FILE
    }
}
```

## Other calls ##

  * Choose a directory:
```
  import group.pals.android.lib.ui.filechooser.services.IFileProvider;

  ...
  intent.putExtra(FileChooserActivity._FilterMode, IFileProvider.FilterMode.DirectoriesOnly);
```

  * Multi-selection:
```
  ...
  intent.putExtra(FileChooserActivity._MultiSelection, true);
```

  * Show hidden files:
```
  ...
  intent.putExtra(FileChooserActivity._DisplayHiddenFiles, true);
```

  * Save-as dialog:
```
  ...
  intent.putExtra(FileChooserActivity._SaveDialog, true);
  intent.putExtra(FileChooserActivity._DefaultFilename, "hi there  ;-)");
```

# Tips #

## Themes ##

  * To turn `FileChooserActivity` into a dialog, open _**your**_ `AndroidManifest.xml` and add this:
```
<activity
    android:name="group.pals.android.lib.ui.filechooser.FileChooserActivity"
    android:theme="@android:style/Theme.Dialog" />
```

  * To change theme in runtime, you must set theme in `AndroidManifest.xml` as above. If you don't do that and set theme to dialog via code, the background of `FileChooserActivity` will be _not_ transparent. A correct example:
```
...
intent.putExtra(FileChooserActivity._Theme, android.R.style.Theme_Dialog);
```

# Other Notes #

All configurations with helper class `group.pals.android.lib.ui.filechooser.prefs.DisplayPrefs` must be called before you start `FileChooserActivity`. But you _can_ configure settings in an activity and call `FileChooserActivity` in another activity.