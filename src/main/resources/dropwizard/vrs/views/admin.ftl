<html>
<h1>Welcome Admin.     </h1> <a href="/logout">Logout</a> <br>
<div>
<form method="post" action="/data/add/movie" id="add_form">
    Movie Name : <input type="text" name="name"> <br>
    Movie Type :
    <select name="type" form="add_form">
        <option value="New">New</option>
        <option value="Regular">Regular</option>
        <option value="Old">Old</option>
    </select>
    <br><br><input type="submit" value="Add Movie">
</form>
</div>
<div>
    <#if movies?has_content>
        <b>List Of Available Movies</b> <br/> <br/>
        <table border="true">
            <tr>
                <th>Name</th>
                <th>Type</th>
            </tr>
            <#list movies as movie>
                <tr>
                    <td>${movie.name}</td>
                    <td>${movie.type}</td>
                </tr>
            </#list>
        </table>
    <#else>
        No movies available!
    </#if>
</div>
</html>