<html>
    <a href="/logout">Logout</a>
    <#if moviesToRent?has_content>
        <h1> List of movies rented. </h1><br/>
        <table border="true">
            <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Basic price</th>
                <th>Bonus</th>
                <th>Rental Days</th>
                <th>Rental Price</th>
            <tr>
                <#list moviesToRent as movieToRent>
                <tr>
                    <td>${movieToRent.name}</td>
                    <td>${movieToRent.type}</td>
                    <td>${movieToRent.basicPrice} SEK</td>
                    <td>${movieToRent.bonus}</td>
                    <td>${movieToRent.rentalDays}</td>
                    <td>${movieToRent.rentalPrice} SEK</td>
                </tr>
                </#list>
        </table>
        <br>
        <h2>The total rental amount is ${totalRentalPrice} SEK</h2>
        <br>
    </#if>
    <#if moviesReturned?has_content>
        <h1>You have returned the below movies.</h1><br/>
        <table border="true">
            <tr>
                <th rowspan="2">Name</th>
                <th rowspan="2">Type</th>
                <th colspan="2">Rental Days</th>
                <th rowspan="2">Rental price</th>
                <th rowspan="2">Surcharges</th>
                <th rowspan="2">Total price</th>
            </tr>
            <tr>
                <th>Original</th>
                <th>Actual</th>
            </tr>
            <#list moviesReturned as returnedMovie>
                <#assign rentalPrice = returnedMovie.rentalPrice>
                <#assign surcharges =  returnedMovie.surcharges>
                <#assign totalPrice = rentalPrice + surcharges>
                <tr>
                    <td>${returnedMovie.name}</td>
                    <td>${returnedMovie.type}</td>
                    <td>${returnedMovie.rentalDays}</td>
                    <td>${returnedMovie.numberOfDaysRented}</td>
                    <td>${rentalPrice} SEK</td>
                    <td>${surcharges} SEK</td>
                    <td>${totalPrice} SEK</td>
                </tr>
            </#list>
        </table>
        <br>
        <#if totalSurcharges gt 0>
            <h2>Please pay a surcharge of ${totalSurcharges} SEK <h2>
        <#else >
            <h2>No surcharges
        </#if>
    </#if>
</html>