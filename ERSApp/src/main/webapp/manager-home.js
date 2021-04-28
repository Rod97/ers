$(function () {
    var showTable = false;
    (function () {
        fetch('http://localhost:8080/ERSApp/display', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => res.json()).then(employeeInfo => {
            for (let employee in employeeInfo) {
                let tableRow = document.createElement("TR");
                tableRow.setAttribute("id", employeeInfo[employee].id)
                tableRow.addEventListener("click", function () {
                    showEmployeeRequests(employeeInfo[employee].id)
                });
                let tableData = document.createElement("TD");
                data = document.createTextNode(employeeInfo[employee].id);

                tableData.appendChild(data);
                tableRow.appendChild(tableData);
                tableData = document.createElement("TD");
                data = document.createTextNode(employeeInfo[employee].name);
                tableData.appendChild(data);
                tableRow.appendChild(tableData);
                tableData = document.createElement("TD");
                data = document.createTextNode(employeeInfo[employee].email);
                tableData.appendChild(data);
                tableRow.appendChild(tableData);

                document.getElementById("employees").appendChild(tableRow);

            }
        });
    })();

    function showEmployeeRequests(empID) {
        fetch('http://localhost:8080/ERSApp/display', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                employeeID: empID
            })
        }).then(res => res.json()).then(empRequests => {

            if (Object.keys(empRequests).length != 0) {
                showTable = true;
                let bigRow = document.createElement("TR");

                let innerTable = document.createElement("TABLE");
                let innerHeader = document.createElement("THEAD");
                let innerHeaderRow = document.createElement("TR");

                let headerElement = document.createElement("TH");
                let headerText = document.createTextNode("Request ID");

                headerElement.appendChild(headerText);

                innerHeaderRow.appendChild(headerElement);

                headerElement = document.createElement("TH");
                headerText = document.createTextNode("Requesting");

                headerElement.appendChild(headerText);

                innerHeaderRow.appendChild(headerElement);

                headerElement = document.createElement("TH");
                headerText = document.createTextNode("Status")

                headerElement.appendChild(headerText);

                innerHeaderRow.appendChild(headerElement);

                headerElement = document.createElement("TH");
                headerText = document.createTextNode("Reason")

                headerElement.appendChild(headerText);

                innerHeaderRow.appendChild(headerElement);

                headerElement = document.createElement("TH");
                headerText = document.createTextNode("Receipt")

                headerElement.appendChild(headerText);

                innerHeaderRow.appendChild(headerElement);

                headerElement = document.createElement("TH");
                headerText = document.createTextNode("Decision")

                headerElement.appendChild(headerText);

                innerHeaderRow.appendChild(headerElement);

                innerHeader.appendChild(innerHeaderRow);

                innerTable.appendChild(innerHeader);

                var tableBody = document.createElement("TBODY")

                console.log(empRequests)

                for (let request in empRequests) {

                    if (empRequests[request].status === "Pending") {
                        tableBody.innerHTML += "<tr><td>" + empRequests[request].reqId + "</td>"
                            + "<td>" + empRequests[request].requesting + "</td>"
                            + "<td>" + empRequests[request].status + "</td>"
                            + "<td>" + empRequests[request].reason + "</td>"
                            + "<td>" + empRequests[request].receipt + "</td>"
                            + "<td><input type='hidden' name='requestId' form='resolved' value=" + empRequests[request].reqId + ">"
                            + "<select name='decision' form='resolved'>"
                            + "<option value='0'>Deny</option>"
                            + "<option value='1'>Accept</option>"
                            + "<option value='-1' selected='selected'>--Options--</option></select></td></tr>";
                    } else {
                        tableBody.innerHTML += "<tr><td>" + empRequests[request].reqId + "</td>"
                            + "<td>" + empRequests[request].requesting + "</td>"
                            + "<td>" + empRequests[request].status + "</td>"
                            + "<td>" + empRequests[request].reason + "</td>"
                            + "<td>" + empRequests[request].receipt + "</td>";
                    }
                }

                innerTable.appendChild(tableBody);
                bigRow.appendChild(document.createElement("td"));
                bigRow.appendChild(innerTable);

                var submitButton = document.createElement("td");
                var theButtonIteself = document.createElement("input");
                theButtonIteself.setAttribute("type", "submit");
                theButtonIteself.setAttribute("form", "resolved");
                theButtonIteself.setAttribute("value", "Resolve Requests");
                submitButton.appendChild(theButtonIteself);

                bigRow.appendChild(submitButton);
                bigRow.setAttribute("id", "nestedTable");

                document.getElementById(empID).parentNode.insertBefore(bigRow, document.getElementById(empID).nextSibling);
                let oldRow = document.getElementById(empID);
                let newRow = oldRow.cloneNode(true);
                oldRow.parentNode.replaceChild(newRow, oldRow);
            }
        });
    }
    $("tr").click(function () {
        var currentId = $(this).getAttribute("id");
        $("#" + currentId).on("click", function () {
            $("nestedTable").toggle();
        })
    })

});