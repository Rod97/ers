$(function () {
    document.getElementById("pendingTab").addEventListener("click", showPending);
    document.getElementById("acceptedTab").addEventListener("click", showAccepted);
    document.getElementById("rejectedTab").addEventListener("click", showRejected);

    function showPending() {
        document.getElementById("pending").setAttribute("class", "nav-link active");
        document.getElementById("accepted").setAttribute("class", "nav-link");
        document.getElementById("rejected").setAttribute("class", "nav-link");

        fetch('http://localhost:8080/ERSApp/show', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                show: 1
            })
        }).then(res => res.json()).then(requests => {
            console.log(requests);
            let element = document.getElementById("requests");
            while (element.firstChild) {
                element.removeChild(element.firstChild);
            }

            for (let request in requests) {
                document.getElementById("requests").innerHTML += "<tr><td>" + requests[request].reqId + "</td><td>" + requests[request].requesting + "</td><td>" + requests[request].status + "</td><td>" + requests[request].reason + "</td><td>" + requests[request].reciept + "</td></tr>";
            }

        });

    }

    function showAccepted() {
        document.getElementById("pending").setAttribute("class", "nav-link");
        document.getElementById("accepted").setAttribute("class", "nav-link active");
        document.getElementById("rejected").setAttribute("class", "nav-link");


        fetch('http://localhost:8080/ERSApp/show', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                show: 2
            })
        }).then(res => res.json()).then(requests => {
            console.log(requests);
            let element = document.getElementById("requests");
            while (element.firstChild) {
                element.removeChild(element.firstChild);
            }


            for (var request in requests) {
                document.getElementById("requests").innerHTML += "<tr><td>"+requests[request].reqId+"</td><td>"+requests[request].requesting+"</td><td>"+requests[request].status+"</td><td>"+requests[request].reason+"</td><td>"+requests[request].reciept+"</td></tr>";
            }
        });
    }

    function showRejected() {
        console.log("inside rejected")
        document.getElementById("pending").setAttribute("class", "nav-link");
        document.getElementById("accepted").setAttribute("class", "nav-link");
        document.getElementById("rejected").setAttribute("class", "nav-link active");


        fetch('http://localhost:8080/ERSApp/show', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                show: 3
            })
        }).then(res => res.json()).then(requests => {
            console.log(requests);

            let element = document.getElementById("requests");
            while (element.firstChild) {
                element.removeChild(element.firstChild);
            }

            var tableData;
            for (let request in requests) {
                document.getElementById("requests").innerHTML += "<tr><td>"+requests[request].reqId+"</td><td>"+requests[request].requesting+"</td><td>"+requests[request].status+"</td><td>"+requests[request].reason+"</td><td>"+requests[request].reciept+"</td></tr>";
            }
        });
    }

});