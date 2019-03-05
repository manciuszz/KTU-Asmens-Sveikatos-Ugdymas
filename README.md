# KTU Asmens Sveikatos Ugdymas

Is a neat android application for KTU Students to download and review their official campus schedule, track their eating habbits etc.. 
![APP Main Activity](/media/app.png)
Unfortunately for us, this app is quite old (last update was on ___October 21, 2015___) and seems to be already broken and abandoned.
![App broken evidence](/media/reviews.png)
The app was created by [Rokas Milasevicius](https://github.com/milasevicius) and uploaded to [Google Play Store](https://play.google.com/store/apps/details?id=app.asu).

## So what's up with this repo?

Well, the good news is, I decided to just decompile (because I couldn't find the source code) and edit the app myself, then post it here to share it with y'all.

#### `NOTICE: I did this without permission, but since the app is free and MEANT to be used by the public and I don't see any potential damage done to the original author, I've decided to go along with it. Furthermore, I doubt the original author even cares (and if you do care, just hit me up on e-mail, I'll just remove this repo).`

#### What was the problem?

Well honestly, the main issue with the app was due to unreachable / no longer existing API Endpoints making the app resolve them indefinitely without any timeouts (even though they are implemented).

Another issue was the questionaire that gets booted up the first time a user opens the application. Since the API Endpoint is no longer reachable, you can't really send the answer to the server, therefore the app seems to be unable to proceed to the next step (the main activity window).

### The FIX

I just simply removed the questionaire and CheckForUpdates function calls from the **StartActivity.java**. That is all - works like a charm.

# APP Permissions
![APP Permissions](/nedia/required_app_permissions.png)

