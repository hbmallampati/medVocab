MedVocab


A medical vocabulary learning Android application for the healthcare consumers, students preparing for CMA, and professionals as well as anyone who wants to keep up with medical terms used in today’s healthcare sector. 

Overall ecosystem
The application ecosystem consists of –
•	Android phone running “MedVocab” application
•	Firebase authentication gateway for Cloud Firestore authentication. The application has both SignUp and SigIn functionality. 
•	Cloud firestore storage unit storing medical vocabulary terms categorized based on level on difficulty. Each user will have their own profile created and stored for them when they SignUp. As users use the application and learn, re-learn, review, master the words their progress is saved in firestore. Users can come back and start learning from where they left. 
•	Merriam-Webster’s medical dictionary API for fetching medical vocabulary word’s meaning, grammatical details. This is a third part API provided by Merriam Webster, this is an up-to-date dictionary of medical terms and definitions and is designed for health-care professionals or anyone who needs explanations of current medical vocabulary. More than 60,000 entries. Covers the latest brand names and generic equivalents of common drugs.
•	Merriam-Webster’s medical audio API used to get the medical term’s pronunciation. In the medical audio API pronunciation is provided for most entries.

Application wireframes:
1.	SingUp functionality
<img width="371" alt="image" src="https://user-images.githubusercontent.com/98439391/212522483-b81a6776-b905-49c8-80c1-87e3c7cd2201.png">

2.	SignIn functionality
  <img width="362" alt="image" src="https://user-images.githubusercontent.com/98439391/212522540-79c85aa8-f9ef-49c3-9703-1c2417611481.png">                                
Fig: Sign in screens

3.	Study fragment
 The Study fragment will list the types/ categories of medical vocabulary words in a recycler view as show below – 
 <img width="317" alt="image" src="https://user-images.githubusercontent.com/98439391/212522552-e452aa20-381f-40ca-bae7-07972457cec9.png">
                                
4.	Medical vocab words are displayed, users progress is tracked, and app has in inbuilt learning algorithm, so you master each word. 

                              




5.	Learning algorithm















6.	About Fragment and Settings fragment


























APIs used
1.	Merriam-Webster’s medical vocabulary dictionary for medical vocabulary terms meanings
2.	Merriam-Webster’s medical audio API for fetching mp3 format pronunciation files for medical terminologies
3.	Firebase authentication for new account creation, sign in with the help of email and password, to create user specific profiles on Firebase cloudstore.
4.	Firebase cloudstore for storing medical vocabulary terms, storing user progress.

Third part libraries used
1.	Retrofit - For creating, sending, and receiving http requests and responses (consuming RESTful web services). For generating GET requests and handling their responses which making API calls to fetch data from Merriam-Webster’s medical dictionary APIs.
2.	OkHttp - For building dictionary APIs http url, for building http client.


Firebase cloustore database schema
 
