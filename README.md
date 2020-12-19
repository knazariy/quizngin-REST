# quizngin-REST
### Web Quiz Engine REST API. Created with Spring Framework. Using H2 Database.
<hr/>

## Features:
- Registering and logging into an account
- Creating and sharing your quizzes among users
- Create multiple-choice quizzes
- Delete your old quizzes, in case you got bored with them
- See the quizzes with paging
- See the record of your successful quiz solutions

Front-end is to be implemented soon..

## Usage

Keep in mind: when requesting quizzes, you get a paginated result. The quizzes are given under the `content` child of a received response.
<br/>

✅ Register using POST with fields `email, password` at `api/register`.<br/>
Body example:
<pre>
{
   "email":"user",
   "password":"12345"
}
</pre>
<br/>

✅ Discover new quizzes with GET at `api/quizzes`.
<br/>

✅ Share your own quiz with POST at `api/quizzes`.<br/>
Your quizzes may have a title, some text, multiple options and answers.<br/>
Body example: 
<pre>
{
   "title":"My quiz",
   "text":"What's the ultimate answer to life, the universe, and everything?",
   "options": ["42", "101010", "yes", "flying saucer"],
   "answer": [0]
}
</pre>
<br/>

✅ Solve quizzes by with POST at `api/quizzes/{id}/solve`, where `id` is the id of the preferred quiz.<br/>
The body may contain `answer` field. Body example:<br/>
<pre>
{
  "answer":[0]
}
</pre>
<br/>

✅ Check what quizzes you already completed with GET at `api/quizzes/completed`. Get the full list of quizzes, as well as the date and time.
<br/>

Play with it on Heroku!
Link: https://quizngin.herokuapp.com/

