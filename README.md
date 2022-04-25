<h1>Star Entertainment Group: The Star Club App- READ ME</h1>

Screen layout (InVision): <a href="https://app.diagrams.net">https://app.diagrams.net</a>

<h3>Application Architecture</h3>
 
To create a maintainable application I have decided to create this app using the architectural parent (MVVM - Model View ViewModel). This design architecture will allow me to create an app, that is extendable and extremely easy to maintain. This design pattern is fully supported and encouraged by google and includes all of their first party libraries. 
 
 
 
 <div>
   <table width="50%" height="50%" align="center" valign="center">
   <tr><td>
      <img src="https://user-images.githubusercontent.com/48664320/164951850-9ae1cad9-4dc2-4b4d-bbfd-0bfc432b7b62.png" alt="MVVM"/>
   </td></tr>
   </table>
</div>
 
 <ul>
 <li><b>Model</b></li>
 This is where all of our business related code will be stored. The model will act as the system to acquire data from our network / database. 
 
 
 <li><b>View</b></li>
  This is the part of the app which concerns each part of the app the the user interacts with. Views handle only the immediate interactions between users   and the app itself. So everything visible on the app will be displayed using views. Views will be straight forward and will not concern themselves with any business logic or data manipulations. 
  
 <li><b>ViewModel</b></li>
  Most if not all of the view logic will be handled by the ViewModel. ViewModels will give views directions and instructions on what to display and govern most of the UI logic. ViewModels are the transmission lanes between the views and the business logic of our code they will grab data from the repositories and  transmit it over to the views to display. 
</ul>

<h3>Data Architecture</h3>


<h3>Network Architecture</h3>
 
Currently there are 2 APIs serving the information required to display in app!

User Data: <a href="https://run.mocky.io/v3/c1819867-9260-4d1e-b9e1-3a77372c83df">https://run.mocky.io/v3/c1819867-9260-4d1e-b9e1-3a77372c83df</a>

Benefits Data: <a href="https://run.mocky.io/v3/6bd03c3d-8b70-40fe-b26c-36bfc03296ff">https://run.mocky.io/v3/6bd03c3d-8b70-40fe-b26c-36bfc03296ff</a>

All network connection in this app is carried out using Volley inside API.kts class. It is important for all network operations to be executed in this class in order to be queued and handled properly.

Unlike the database operations, all network operations are handled asynchronously and this app will use EventBus as well to inform the caller of the result.

Volley is a third party application to handle network calls to fetch data from given APIs and will utilize Eventbus to inform us when data has been fetched and results are ready. We can use an Async funct to achieve the same outcome but for a more cleaner, maintainable, extendable and robust code Volley is the one of the best third party library to utilize here. 

Below are the sample of an GET request:

<pre>
<code>

 private void postDataUsingVolley(String name, String job) {
        // url to post our data
        String url = "";
         
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
         
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                loadingPB.setVisibility(View.GONE);
                nameEdt.setText("");
                jobEdt.setText("");
                 
                // on below line we are displaying a success toast message.
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);
                     
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();
                
                 
                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

</code>

</pre>

<h3>Libraries Used</h3>

<ul>
  <li>EventBus (v3.0.0) - <a href="https://github.com/greenrobot/EventBus">https://github.com/greenrobot/EventBus</a></li>
  <li>Volley (v1.0.0) - <a href="https://developer.android.com/training/volley/index.html">https://developer.android.com/training/volley/index.html</a></li>
</ul>

