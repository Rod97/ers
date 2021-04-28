
    function updateInfo() {
        document.getElementById("name").innerHTML = '<input type="text" id="updatedName" required=required>';
        document.getElementById("empEmail").innerHTML = '<input type="email" id="updatedEmail" required="required">';
        document.getElementById("update/submit").innerHTML = "Submit";

        document.getElementById("update/submit").setAttribute("onclick", "submitUpdate()")
    }

    function submitUpdate() {
        fetch('http://localhost:8080/ERSApp/info', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: document.getElementById("updatedName").value,
                email: document.getElementById("updatedEmail").value
            }) 
        }).then(res => {
            console.log("response")
            return res.json()
        })
            .then(employee => {
                let employeeFirstName = employee.name.split(" ");

                console.log(employeeFirstName[0]);
                document.getElementById("headerName").innerHTML = "<h1>Welcome " + employeeFirstName[0] + "</h1>";
                document.getElementById("name").innerHTML = employee.name;
                document.getElementById("empEmail").innerHTML = employee.email;
                document.getElementById("update/submit").innerHTML = "Update";
                document.getElementById("update/submit").setAttribute("onclick", "updateInfo()")
            })
    }


   