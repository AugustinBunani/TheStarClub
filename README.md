<h1>Star Entertainment Group: The Star Club App- READ ME</h1>

Screen layout (InVision): <a href="https://app.diagrams.net">https://app.diagrams.net</a>

<h3>Application Architecture</h3>
 
To create a maintainable application I have decided to create this app using the architectural parent (MVVM - Model View ViewModel). This design architecture will allow me to create an app, that is extendable and extremely easy to maintain. This design pattern is fully supported and encouraged by google and includes all of their first party libraries. 
 
 <img width="530" margin="auto" alt="Screen Shot 2022-04-24 at 11 20 15 am" src="https://user-images.githubusercontent.com/48664320/164951627-ed7ddb19-dee2-491f-ad08-eeda053cf3df.png">
 
 <ul>
  <li>Model</li>
  
  <li>View</li>
  This is the part of the app which concerns each part of the app the the user interacts with. Views handle only the immediate interactions between users   and the app itself. So everything visible on the app will be displayed using views. Views will be straight forward and will not concern themselves with any business logic or data manipulations. 
  
  <li>ViewModel</li>
  Most if not all of the view logic will be handled by the ViewModel. ViewModels will give views directions and instructions on what to display and govern most of the UI logic. ViewModels are the transmission lanes between the views and the business logic of our code they will grab data from the repositories and  transmit it over to the views to display. 
</ul>

<h3>Data Architecture</h3>



<h3>Network Architecture</h3>
 
Currently there are 2 APIs serving the information required to display in app!

User Data: <a href="https://run.mocky.io/v3/c1819867-9260-4d1e-b9e1-3a77372c83df">https://run.mocky.io/v3/c1819867-9260-4d1e-b9e1-3a77372c83df</a>

Benefits Data: <a href="https://run.mocky.io/v3/6bd03c3d-8b70-40fe-b26c-36bfc03296ff">https://run.mocky.io/v3/6bd03c3d-8b70-40fe-b26c-36bfc03296ff</a>


Below are the sample of an GET request:

<pre>
<code>

</code>

</pre>

<h3>Libraries Used</h3>
<ul>

</ul>

