function loadXMLDoc() {
    var xmlhttp;
    var requestBody={};
    requestBody = JSON.Stringify(document.getElementById("uName").value);
    var password = JSON.Stringify(document.getElementById("psw").value);
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for older browsers
        xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
    }
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          document.getElementById("container").innerHTML =
          this.responseText;
        }
    };
    xmlhttp.open('GET', 'http://localhost:8080/DecoroCoppette/findAllDecoriCoppette');
    xmlhttp.responseType = 'json';
    xmlhttp.onload = function(e) {
        if (this.status == 200) {
            console.log('response', this.response); // JSON response
        }
    };
    xmlhttp.send();
}


function postRequest(){
    const data = JSON.stringify({
      name: 'Roger',
      age: 8
    })

    const xhr = new XMLHttpRequest()
    xhr.withCredentials = true

    xhr.addEventListener('readystatechange', function() {
      if (this.readyState === this.DONE) {
        console.log(this.responseText)
      }
    })

    xhr.open('POST', 'https://api.randomservice.com/dog')
    xhr.setRequestHeader('content-type', 'application/json')
    xhr.setRequestHeader('authorization', 'Bearer 123abc456def')

    xhr.send(data)
}