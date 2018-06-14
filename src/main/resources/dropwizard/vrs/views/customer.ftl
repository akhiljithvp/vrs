<html>
<body>
<h1> Welcome ${customer.name}!</h1>
<a href="/logout">Logout</a>
<h1> You have ${customer.bonus} bonus points.</h1>

<p>
    <h2> Price and surcharge information </h2>
    1. NEW - Price is 40 SEK times number of days rented. <br/>
    2. OLD - Price is 30 SEK for the first 5 days and then 30 SEK times the number of days over 5 <br/>
    3. REGULAR - Price is 30 SEK for the first 3 days and then 30 SEK times the number of days over 3. <br/>
</p>

<br/>
<div>
    <form method="post" action="/data/movies/save?customer=${customer.name}">
        <#if movies??>
            <h2>List Of Movies</h2>
            <table border="true">
                <tr>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Options</th>
                </tr>
                <#assign rowNumber=0>
                <#list movies as movie>
                    <#assign text='Rent'>
                    <#if !movie.availableForRent>
                        <#assign text='Return'>
                    </#if>
                    <tr>
                        <td>${movie.name}</td>
                        <td>${movie.type}</td>
                        <td><input type="checkbox" name="selectedMovies" value="${movie.name}"/>${text}</td>
                    </tr>
                    <#assign rowNumber=rowNumber+1>
                </#list>
            </table>
            Number of days to rent : <input type="number" name="days"/>
        <#else>
            No movies available!
        </#if>
        <br> <br>
        <input type='submit' value='Finish'/>
        <br>
    </form>
</div>
</body>
</html>
