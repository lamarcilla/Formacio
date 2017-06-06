using System;
using System.Runtime.InteropServices;
using System.ComponentModel;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Security;
using System.Security.Permissions;
using Microsoft.Win32.SafeHandles;
using System.Runtime.ConstrainedExecution;


using System.Threading;
using System.IO;
using System.IO.MemoryMappedFiles;

using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

using System.Runtime.ExceptionServices;
using System.Reflection;
using System.Collections;
using System.Net;
using System.Configuration;
using System.Security.Cryptography;



namespace ConsoleApplication1
{
    class Program
    {
        string stringUri;
        Uri uriUri;
        private string[] pages = new string[10];

        static void Main(string[] args)
        {
        }


        // *****************
        // CODIGO: fxcop:UriParametersShouldNotBeStrings
        // REGLA: CA1054: URI parameters should not be strings
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void AddToHistory(string uriString) { }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        /// <param name="uriType"></param>
        public void AddToHistoryResuelto(Uri uriType) { }



        // *****************
        // CODIGO: fxcop:UriReturnValuesShouldNotBeStrings
        // REGLA: CA1055: URI return values should not be strings
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        /// <param name="httpHeader"></param>
        /// <returns></returns>
        public string GetRefererUri(string httpHeader)
        {
            return "http://www.adventure-works.com";
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        /// <param name="httpHeader"></param>
        /// <returns></returns>
        public Uri GetRefererUriResuelto(string httpHeader)
        {
            return new Uri("http://www.adventure-works.com");
        }



        // *****************
        // CODIGO: fxcop:UriPropertiesShouldNotBeStrings
        // REGLA: CA1056: URI properties should not be strings
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public string StringUri
        {
            get { return stringUri; }
            set { stringUri = value; }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public Uri UriUri
        {
            get { return uriUri; }
            set { uriUri = value; }
        }



        // *****************
        // CODIGO: fxcop:StringUriOverloadsCallSystemUriOverloads
        // REGLA: CA1057: String URI overloads call System.Uri overloads
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void AddToHistory2(string uriString) { }

        public void AddToHistory2(Uri uriType) { }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void AddToHistory3(string uriString)
        {
            Uri newUri = new Uri(uriString);
            AddToHistory3(newUri);
        }

        public void AddToHistory3(Uri uriType) { }



        // *****************
        // CODIGO: fxcop:MovePInvokesToNativeMethodsClass
        // REGLA: CA1060: Move P/Invokes to NativeMethods class
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [DllImport("kernel32.dll", CharSet = CharSet.Unicode)]
        internal static extern bool RemoveDirectory(string name);


        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO 1
        /// </summary>
        internal static class NativeMethods
        {
            [DllImport("user32.dll", CharSet = CharSet.Auto)]
            [return: MarshalAs(UnmanagedType.Bool)]
            internal static extern bool MessageBeep(int uType);
        }

        // Callers require Unmanaged permission        
        public static void Beep()
        {
            // No need to demand a permission as callers of Interaction.Beep            
            // will require UnmanagedCode permission            
            if (!NativeMethods.MessageBeep(-1))
                throw new Win32Exception();
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO 2
        /// </summary>
        [SuppressUnmanagedCodeSecurityAttribute]
        internal static class SafeNativeMethods
        {
            [DllImport("kernel32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
            internal static extern int GetTickCount();
        }
        
        // Callers do not require UnmanagedCode permission       
        public static int TickCount
        {
            get
            {
                // No need to demand a permission in place of               
                // UnmanagedCode as GetTickCount is considered              
                // a safe method              
                return SafeNativeMethods.GetTickCount();
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO 3
        /// </summary>
        [SuppressUnmanagedCodeSecurityAttribute]
        internal static class UnsafeNativeMethods
        {
            [DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
            internal static extern int ShowCursor([MarshalAs(UnmanagedType.Bool)]bool bShow);
        }

        // Callers do not require UnmanagedCode permission, however,       
        // they do require UIPermissionWindow.AllWindows       
        public static void Hide()
        {
            // Need to demand an appropriate permission           
            // in  place of UnmanagedCode permission as            
            // ShowCursor is not considered a safe method           
            new UIPermission(UIPermissionWindow.AllWindows).Demand();
            UnsafeNativeMethods.ShowCursor(false);
        }



        // *****************
        // CODIGO: fxcop:PInvokesShouldNotBeVisible
        // REGLA: CA1401: P/Invokes should not be visible
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class NativeMethods1
        {
            [DllImport("kernel32.dll", CharSet = CharSet.Unicode)]
            public static extern bool RemoveDirectory(string name);
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        internal static class NativeMethodsResuelto1
        {
            [DllImport("kernel32.dll", CharSet = CharSet.Unicode)]
            internal static extern bool RemoveDirectory(string name);
        }



        // *****************
        // CODIGO: fxcop:PropertiesShouldNotReturnArrays
        // REGLA: CA1819: Properties should not return arrays
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public string[] Pages1
        {
            get { return pages; }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public string[] GetPages()
        {
            // Need to return a clone of the array so that consumers             
            // of this library cannot change its contents             
            return (string[])pages.Clone();
        }



        // *****************
        // CODIGO: fxcop:UseSafeHandleToEncapsulateNativeResources
        // REGLA: CA2006: Use SafeHandle to encapsulate native resources
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void usarInPtr()
        {
            string stringA = "I seem to be turned around!";

            IntPtr sptr = Marshal.StringToHGlobalAnsi(stringA);

            // ...
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void usarSafeHandler()
        {
            SafeMemoryMappedViewHandle safeHandler = MemoryMappedFile.CreateNew("stringA", 100).CreateViewAccessor().SafeMemoryMappedViewHandle;

            // ...
        }



        // *****************
        // CODIGO: fxcop:ReviewSqlQueriesForSecurityVulnerabilities
        // REGLA: CA2100: Review SQL queries for security vulnerabilities
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void construirSQL()
        {
            int x = 10;
            string query1 = "SELECT TOP " + x.ToString() + " FROM Table";
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void construirSQLResuelto()
        {
            int x = 10;
            string query2 = String.Format("SELECT TOP {0} FROM Table", x);
        }


        // *****************
        // CODIGO: fxcop:SpecifyMarshalingForPInvokeStringArguments
        // REGLA: CA2101: Specify marshaling for P/Invoke string arguments
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        class NativeMethods2
        {
            [DllImport("advapi32.dll", CharSet = CharSet.Auto)]
            internal static extern int RegCreateKey(IntPtr key, String subKey, out IntPtr result);
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        class NativeMethodsResuelto2
        {
            [DllImport("advapi32.dll", CharSet = CharSet.Unicode)]
            internal static extern int RegCreateKey2(IntPtr key, String subKey, out IntPtr result);
        }



        // *****************
        // CODIGO: fxcop:CatchNonClsCompliantExceptionsInGeneralHandlers
        // REGLA: CA2102: Catch non-CLSCompliant exceptions in general handlers
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        void CatchAllExceptions()
        {
            try
            {
                // Código que puede devolver una excepción non-ClsCompliant;
            }
            catch (Exception e)
            {
                // Remove some permission.
                Console.WriteLine("CLS compliant exception caught");
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        void CatchAllExceptionsResuelto()
        {
            try
            {
                // Código que puede devolver una excepción non-ClsCompliant;
            }
            catch (Exception e)
            {
                // Remove some permission.
                Console.WriteLine("CLS compliant exception caught");
            }
            catch
            {
                // Remove the same permission as above.
                Console.WriteLine("Non-CLS compliant exception caught.");
            }
        }



        // *****************
        // CODIGO: fxcop:ReviewImperativeSecurity
        // REGLA: CA2103: Review imperative security
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        class MyPermission : CodeAccessPermission
        {
            public override SecurityElement ToXml() { return null; }
            public override void FromXml(SecurityElement elem) { }
            public override IPermission Copy() { return this; }
            public override IPermission Intersect(IPermission target) { return this; }
            public override bool IsSubsetOf(IPermission target) { return false; }
        }

        public void MyMethod()
        {
            //MyPermission is demanded using imperative syntax.
            MyPermission Perm = new MyPermission();
            Perm.Demand();
            //This method is protected by the security call.
        }

        // RESOLUCIÓN DE INCUMPLIMIENTO
        // Asegurarse de que MyPermission permanece estable con los datos


        // *****************
        // CODIGO: fxcop:DoNotDeclareReadOnlyMutableReferenceTypes
        // REGLA: CA2104: Do not declare read only mutable reference types
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        static protected readonly StringBuilder SomeStringBuilder = new StringBuilder();

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        static protected readonly String SomeString = "Contenido inmutable";



        // *****************
        // CODIGO: fxcop:ArrayFieldsShouldNotBeReadOnly
        // REGLA: CA2105: Array fields should not be read only
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public readonly int[] publicGrades = { 90, 90, 90 };

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        private readonly int[] privateGrades = { 90, 90, 90 };
        public int[] GetSecurePrivateGrades()
        {
            return (int[])privateGrades.Clone();
        }



        // *****************
        // CODIGO: fxcop:SecureAsserts
        // REGLA: CA2106: Secure asserts
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void usoAssert()
        {
            SecurityPermission Permiso = new SecurityPermission(PermissionState.None);
            Permiso.Assert();

            // ...
        }
        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void usoAssertResuelto()
        {
            SecurityPermission Permiso = new SecurityPermission(PermissionState.None);
            Permiso.Demand();
            Permiso.Assert();

            // ...
        }



        // *****************
        // CODIGO: fxcop:ReviewDenyAndPermitOnlyUsage
        // REGLA: CA2107: Review deny and permit only usage
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void usoPermitOnly()
        {
            SecurityPermission Permiso = new SecurityPermission(PermissionState.None);
            Permiso.PermitOnly();

            // ...
        }
        public void usoDeny()
        {
            SecurityPermission Permiso = new SecurityPermission(PermissionState.None);
            Permiso.Deny();

            // ...
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void usoPermitOnlyResuelto()
        {
            SecurityPermission Permiso = new SecurityPermission(PermissionState.None);
            Permiso.Demand();

            // ...
        }
        public void usoDenyResuelto()
        {
            // Eliminar el uso de "Deny"
            // ...
        }



        // *****************
        // CODIGO: fxcop:ReviewDeclarativeSecurityOnValueTypes
        // REGLA: CA2108: Review declarative security on value types
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [System.Security.Permissions.PermissionSetAttribute(System.Security.Permissions.SecurityAction.Demand, Name = "FullTrust")]
        public struct SecuredTypeStructure
        {
            internal double xValue;
            internal double yValue;
        
            public SecuredTypeStructure(double x, double y)
            {
                xValue = x;
                yValue = y;
            }
        }

        public class StructureManager
        {
            // This method asserts trust, incorrectly assuming that the caller must have 
            // permission because they passed in instance of the value type.
            [System.Security.Permissions.PermissionSetAttribute(System.Security.Permissions.SecurityAction.Assert, Name = "FullTrust")]
            public static SecuredTypeStructure AddStepValue(SecuredTypeStructure aStructure)
            {
                aStructure.xValue += 100;
                aStructure.yValue += 100;
                return aStructure;
            }
        }

        // RESOLUCIÓN DE INCUMPLIMIENTO
        [System.Security.Permissions.PermissionSetAttribute(System.Security.Permissions.SecurityAction.Demand, Name = "FullTrust")]
        public class SecuredTypeStructureResuelto
        {
            internal double xValue;
            internal double yValue;

            public SecuredTypeStructureResuelto(double x, double y)
            {
                xValue = x;
                yValue = y;
            }
        }

        public class StructureManagerResuelto
        {
            // This method asserts trust, incorrectly assuming that the caller must have 
            // permission because they passed in instance of the value type.
            [System.Security.Permissions.PermissionSetAttribute(System.Security.Permissions.SecurityAction.Assert, Name = "FullTrust")]
            public static SecuredTypeStructureResuelto AddStepValue(SecuredTypeStructureResuelto aStructure)
            {
                aStructure.xValue += 100;
                aStructure.yValue += 100;
                return aStructure;
            }
        }



        // *****************
        // CODIGO: fxcop:ReviewVisibleEventHandlers
        // REGLA: CA2109: Review visible event handlers
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class HandleEvents
        {
            // Due to the access level and signature, a malicious caller could 
            // add this method to system-triggered events where all code in the call
            // stack has the demanded permission.

            // Also, the demand might be canceled by an asserted permission.

            [SecurityPermissionAttribute(SecurityAction.Demand, UnmanagedCode = true)]

            // Violates rule: ReviewVisibleEventHandlers.
            public static void SomeActionHappened(Object sender, EventArgs e)
            {
                Console.WriteLine("Do something dangerous from unmanaged code.");
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class HandleEventsResuelto
        {
            [SecurityPermissionAttribute(SecurityAction.Demand, UnmanagedCode = true)]

            // Se cambia la visibilidad
            protected static void SomeActionHappened(Object sender, EventArgs e)
            {
                Console.WriteLine("Do something dangerous from unmanaged code.");
            }
        }



        // *****************
        // CODIGO: fxcop:PointersShouldNotBeVisible
        // REGLA: CA2111: Pointers should not be visible
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public UIntPtr publicPointer;
        protected IntPtr protectedPointer;


        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public readonly UIntPtr publicReadOnlyPointer;
        protected readonly IntPtr protectedReadOnlyPointer;



        // *****************
        // CODIGO: fxcop:SecuredTypesShouldNotExposeFields
        // REGLA: CA2112: Secured types should not expose fields
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        // This code requires immediate callers to have full trust.
        [System.Security.Permissions.PermissionSetAttribute(
            System.Security.Permissions.SecurityAction.LinkDemand,
            Name = "FullTrust")]
        public class SecuredTypeWithFields
        {
            // Even though the type is secured, these fields are not.
            // Violates rule: SecuredTypesShouldNotExposeFields.
            public double xValue;
            public double yValue;

            public SecuredTypeWithFields(double x, double y)
            {
                xValue = x;
                yValue = y;
            }
        }


        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        // This code requires immediate callers to have full trust.
        [System.Security.Permissions.PermissionSetAttribute(
            System.Security.Permissions.SecurityAction.LinkDemand,
            Name = "FullTrust")]
        public class SecuredTypeWithFieldsResuelto
        {
            // Pasando a privados los campos se resuelve la vulnerabilidad.
            private double xValue;
            private double yValue;

            public SecuredTypeWithFieldsResuelto(double x, double y)
            {
                xValue = x;
                yValue = y;
            }
        }



        // *****************
        // CODIGO: fxcop:MethodSecurityShouldBeASupersetOfType
        // REGLA: CA2114: Method security should be a superset of type
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [EnvironmentPermissionAttribute(SecurityAction.Demand, Write = "PersonalInfo")]
        public class MyClassWithTypeSecurity
        {
            [DllImport("kernel32.dll", CharSet = CharSet.Unicode, SetLastError = true)]
            [return: MarshalAs(UnmanagedType.Bool)]
            public static extern bool SetEnvironmentVariable(
               string lpName,
               string lpValue);

            // Constructor.
            public MyClassWithTypeSecurity(int year, int month, int day)
            {
                DateTime birthday = new DateTime(year, month, day);

                // Write out PersonalInfo environment variable.
                SetEnvironmentVariable("PersonalInfo", birthday.ToString());
            }

            [EnvironmentPermissionAttribute(SecurityAction.Demand, Read = "PersonalInfo")]
            public string PersonalInformation()
            {
                // Read the variable.
                return Environment.GetEnvironmentVariable("PersonalInfo");
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [EnvironmentPermissionAttribute(SecurityAction.Demand, Write = "PersonalInfo")]
        public class MyClassWithTypeSecurityResuelto
        {
            [DllImport("kernel32.dll", CharSet = CharSet.Unicode, SetLastError = true)]
            [return: MarshalAs(UnmanagedType.Bool)]
            public static extern bool SetEnvironmentVariable(
               string lpName,
               string lpValue);

            // Constructor.
            public MyClassWithTypeSecurityResuelto(int year, int month, int day)
            {
                DateTime birthday = new DateTime(year, month, day);

                // Write out PersonalInfo environment variable.
                SetEnvironmentVariable("PersonalInfo", birthday.ToString());
            }

            // Se añade el permiso de escritura (heredado de la clase)
            [EnvironmentPermissionAttribute(SecurityAction.Demand, Write = "PersonalInfo", Read = "PersonalInfo")]
            public string PersonalInformation()
            {
                // Read the variable.
                return Environment.GetEnvironmentVariable("PersonalInfo");
            }
        }



        // *****************
        // CODIGO: fxcop:AptcaMethodsShouldOnlyCallAptcaMethods
        // REGLA: CA2116: APTCA methods should only call APTCA methods
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [assembly:AllowPartiallyTrustedCallers]
        public class AceptaConfianzaParcial
        {
        }

        // RESOLUCIÓN DE INCUMPLIMIENTO
        // Comprobar que no llame a ningún método que espera confianza total.



        // *****************
        // CODIGO: fxcop:AptcaTypesShouldOnlyExtendAptcaBaseTypes
        // REGLA: CA2117: APTCA types should only extend APTCA base types
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void metodoConfianzaParcial()
        {
            // Llamada a método de ensamblado con confianza total
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void metodoConfianzaParcialResuelto()
        {
            // Llamada a método de ensamblado con confianza parcial
        }



        // *****************
        // CODIGO: fxcop:ReviewSuppressUnmanagedCodeSecurityUsage
        // REGLA: CA2118: Review SuppressUnmanagedCodeSecurityAttribute usage
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [SuppressUnmanagedCodeSecurityAttribute()]
        public void DoWork()
        {
            // Cosas peligrosas;
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        // Eliminar el atributo [SuppressUnmanagedCodeSecurityAttribute]
        public void DoWorkResuelto()
        {
            // Cosas con el peligro controlado;
        }



        // *****************
        // CODIGO: fxcop:SealMethodsThatSatisfyPrivateInterfaces
        // REGLA: CA2119: Seal methods that satisfy private interfaces
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        internal interface IValidate
        {
            bool UserIsValidated();
        }

        public class BaseImplementation : IValidate
        {
            public virtual bool UserIsValidated()
            {
                return false;
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        internal interface IValidateResuelto
        {
            bool UserIsValidated();
        }

        public class BaseImplementationResuelto : IValidateResuelto
        {
            public bool UserIsValidated()
            {
                return false;
            }
        }



        // *****************
        // CODIGO: fxcop:SecureSerializationConstructors
        // REGLA: CA2120: Secure serialization constructors
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [Serializable]
        public class SerializationConstructorsRequireSecurity : ISerializable
        {
            private int n1;
            // This is a regular constructor secured by a demand.
            [FileIOPermissionAttribute(SecurityAction.Demand, Unrestricted = true)]
            public SerializationConstructorsRequireSecurity()
            {
                n1 = -1;
            }
            // This is the serialization constructor.
            // Violates rule: SecureSerializationConstructors.
            protected SerializationConstructorsRequireSecurity(SerializationInfo info, StreamingContext context)
            {
                n1 = (int)info.GetValue("n1", typeof(int));
            }
            void ISerializable.GetObjectData(SerializationInfo info, StreamingContext context)
            {
                info.AddValue("n1", n1);
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [Serializable]
        public class SerializationConstructorsRequireSecurityResuelto : ISerializable
        {
            private int n1;
            // This is a regular constructor secured by a demand.
            [FileIOPermissionAttribute(SecurityAction.Demand, Unrestricted = true)]
            public SerializationConstructorsRequireSecurityResuelto()
            {
                n1 = -1;
            }
            // This is the serialization constructor secured by a demand.
            [FileIOPermissionAttribute(SecurityAction.Demand, Unrestricted = true)]
            protected SerializationConstructorsRequireSecurityResuelto(SerializationInfo info, StreamingContext context)
            {
                n1 = (int)info.GetValue("n1", typeof(int));
            }
            void ISerializable.GetObjectData(SerializationInfo info, StreamingContext context)
            {
                info.AddValue("n1", n1);
            }
        }



        // *****************
        // CODIGO: fxcop:DoNotIndirectlyExposeMethodsWithLinkDemands
        // REGLA: CA2122: Do not indirectly expose methods with link demands
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        // Violates rule: DoNotIndirectlyExposeMethodsWithLinkDemands.
        public static string DomainInformation()
        {
            return EnvironmentSetting("USERDNSDOMAIN");
        }

        // Library method with link demand.
        // This method holds its immediate callers responsible for securing the information.
        // Because a caller must have unrestricted permission, the method asserts read permission
        // in case some caller in the stack does not have this permission. 

        [EnvironmentPermissionAttribute(SecurityAction.LinkDemand, Unrestricted = true)]
        public static string EnvironmentSetting(string environmentVariable)
        {
            EnvironmentPermission envPermission = new EnvironmentPermission(EnvironmentPermissionAccess.Read, environmentVariable);
            envPermission.Assert();

            return Environment.GetEnvironmentVariable(environmentVariable);
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [EnvironmentPermissionAttribute(SecurityAction.LinkDemand, Unrestricted = true)]
        public static string DomainInformationResuelto()
        {
            return EnvironmentSettingResuelto("USERDNSDOMAIN");
        }

        [EnvironmentPermissionAttribute(SecurityAction.LinkDemand, Unrestricted = true)]
        public static string EnvironmentSettingResuelto(string environmentVariable)
        {
            EnvironmentPermission envPermission = new EnvironmentPermission(EnvironmentPermissionAccess.Read, environmentVariable);
            envPermission.Assert();

            return Environment.GetEnvironmentVariable(environmentVariable);
        }



        // *****************
        // CODIGO: fxcop:OverrideLinkDemandsShouldBeIdenticalToBase
        // REGLA: CA2123: Override link demands should be identical to base
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public interface ITestOverrides
        {
            [EnvironmentPermissionAttribute(SecurityAction.LinkDemand, Unrestricted = true)]
            Object GetFormat(Type formatType);
        }

        public class OverridesAndSecurity : ITestOverrides
        {
            // Rule violation: The interface has security, and this implementation does not.
            object ITestOverrides.GetFormat(Type formatType)
            {
                return (formatType == typeof(OverridesAndSecurity) ? this : null);
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public interface ITestOverridesResuelto
        {
            [EnvironmentPermissionAttribute(SecurityAction.LinkDemand, Unrestricted = true)]
            Object GetFormat(Type formatType);
        }

        public class OverridesAndSecurityResuelto : ITestOverridesResuelto
        {
            [EnvironmentPermissionAttribute(SecurityAction.LinkDemand, Unrestricted = true)]
            object ITestOverridesResuelto.GetFormat(Type formatType)
            {
                return (formatType == typeof(OverridesAndSecurity) ? this : null);
            }
        }



        // *****************
        // CODIGO: fxcop:WrapVulnerableFinallyClausesInOuterTry
        // REGLA: CA2124: Wrap vulnerable finally clauses in outer try (only version 1.0 / 1.1)
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void FinallyVulnerable()
        {
            try
            {
                // Do some work.  
            }
            finally
            {
                // Reset security state.  
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void FinallyVulnerableResuelto()
        {
            try
            {
                try
                {
                    // Do some work.  
                }
                finally
                {
                    // Reset security state.  
                }
            }
            catch
            {
                throw;
            }
        }



        // *****************
        // CODIGO: fxcop:TypeLinkDemandsRequireInheritanceDemands
        // REGLA: CA2126: Type link demands require inheritance demands
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [EnvironmentPermission(SecurityAction.LinkDemand, Read = "PATH")]
        public class TypesWithLinkDemands
        {
            public virtual void UnsecuredMethod() { }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [EnvironmentPermission(SecurityAction.LinkDemand, Read = "PATH")]
        public class TypesWithLinkDemandsResuelto
        {
            [EnvironmentPermission(SecurityAction.InheritanceDemand, Read = "PATH")]
            public virtual void SecuredMethod() { }
        }



        // *****************
        // CODIGO: fxcop:ConstantsShouldBeTransparent
        // REGLA: CA2130: Security critical constants should be transparent
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public enum EnumWithCriticalValues
        {
            TransparentEnumValue,

            // CA2130 violation
            [SecurityCritical]
            CriticalEnumValue
        }

        public class ClassWithCriticalConstant
        {
            // CA2130 violation
            [SecurityCritical]
            public const int CriticalConstant = 21;
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public enum EnumWithCriticalValuesResuelto
        {
            TransparentEnumValue,
            CriticalEnumValue
        }

        public class ClassWithCriticalConstantResuelto
        {
            public const int CriticalConstant = 21;
        }



        // *****************
        // CODIGO: fxcop:CriticalTypesMustNotParticipateInTypeEquivalence
        // REGLA: CA2131: Security critical types may not participate in type equivalence
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        // CA2131 error - critical type participating in equivilance
        [SecurityCritical]
        [TypeIdentifier("3a5b6203-2bf1-4f83-b5b4-1bdc334ad3ea", "ICriticalEquivilentInterface")]
        public interface ICriticalEquivilentInterface
        {
            void Method1();
        }

        [TypeIdentifier("3a5b6203-2bf1-4f83-b5b4-1bdc334ad3ea", "ITransparentEquivilentInterface")]
        public interface ITransparentEquivilentInterface
        {
            // CA2131 error - critical method in a type participating in equivilance
            [SecurityCritical]
            void CriticalMethod();
        }

        [SecurityCritical]
        [TypeIdentifier("3a5b6203-2bf1-4f83-b5b4-1bdc334ad3ea", "ICriticalEquivilentInterface")]
        public struct EquivilentStruct
        {
            // CA2131 error - critical field in a type participating in equivalence
            [SecurityCritical]
            public int CriticalField;
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [TypeIdentifier("3a5b6203-2bf1-4f83-b5b4-1bdc334ad3ea", "ICriticalEquivilentInterfaceResuelto")]
        public interface ICriticalEquivilentInterfaceResuelto
        {
            void Method1();
        }

        [TypeIdentifier("3a5b6203-2bf1-4f83-b5b4-1bdc334ad3ea", "ITransparentEquivilentInterfaceResuelto")]
        public interface ITransparentEquivilentInterfaceResuelto
        {
            void CriticalMethod();
        }

        [TypeIdentifier("3a5b6203-2bf1-4f83-b5b4-1bdc334ad3ea", "ICriticalEquivilentInterfaceResuelto")]
        public struct EquivilentStructResuelto
        {
            public int CriticalField;
        }



        // *****************
        // CODIGO: fxcop:DefaultConstructorsMustHaveConsistentTransparency
        // REGLA: CA2132: Default constructors must be at least as critical as base type default constructors
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class BaseWithSafeCriticalDefaultCtor
        {
            [SecuritySafeCritical]
            public BaseWithSafeCriticalDefaultCtor() { }
        }
        public class DerivedWithNoDefaultCtor : BaseWithSafeCriticalDefaultCtor
        {
            // CA2132 violation - since the base has a public or protected non-transparent default .ctor, the 
            // derived type must also have a default .ctor
        }
        public class DerivedWithTransparentDefaultCtor : BaseWithSafeCriticalDefaultCtor
        {
            // CA2132 violation - since the base has a safe critical default .ctor, the derived type must have 
            // either a safe critical or critical default .ctor.  This is fixed by making this .ctor safe critical 
            // (however, user code cannot be safe critical, so this fix is platform code only).
            DerivedWithTransparentDefaultCtor() { }
        }
        public class BaseWithCriticalCtor
        {
            [SecurityCritical]
            public BaseWithCriticalCtor() { }
        }
        public class DerivedWithSafeCriticalDefaultCtor : BaseWithSafeCriticalDefaultCtor
        {
            // CA2132 violation - since the base has a critical default .ctor, the derived must also have a critical 
            // default .ctor.  This is fixed by making this .ctor critical, which is not available to user code
            [SecuritySafeCritical]
            public DerivedWithSafeCriticalDefaultCtor() { }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class BaseWithSafeCriticalDefaultCtorResuelto
        {
            [SecuritySafeCritical]
            public BaseWithSafeCriticalDefaultCtorResuelto() { }
        }
        public class DerivedWithNoDefaultCtorResuelto : BaseWithSafeCriticalDefaultCtorResuelto
        {
            [SecuritySafeCritical]
            public DerivedWithNoDefaultCtorResuelto() { }
        }
        public class DerivedWithTransparentDefaultCtorResuelto : BaseWithSafeCriticalDefaultCtorResuelto
        {
            [SecuritySafeCritical]
            DerivedWithTransparentDefaultCtorResuelto() { }
        }
        public class BaseWithCriticalCtorResuelto
        {
            [SecurityCritical]
            public BaseWithCriticalCtorResuelto() { }
        }
        public class DerivedWithSafeCriticalDefaultCtorResuelto : BaseWithCriticalCtorResuelto
        {
            [SecurityCritical]
            public DerivedWithSafeCriticalDefaultCtorResuelto() { }
        }



        // *****************
        // CODIGO: fxcop:DelegatesMustBindWithConsistentTransparency
        // REGLA: CA2133: Delegates must bind to methods with consistent transparency
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public delegate void TransparentDelegate();
        [SecurityCritical]
        public delegate void CriticalDelegate();
        public class TransparentType
        {
            void DelegateBinder()
            {
                // CA2133 violation - binding a transparent delegate to a critical method
                TransparentDelegate td = new TransparentDelegate(CriticalTarget);
                // CA2133 violation - binding a critical delegate to a transparent method
                CriticalDelegate cd = new CriticalDelegate(TransparentTarget);
            }
            [SecurityCritical]
            void CriticalTarget() { }
            void TransparentTarget() { }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public delegate void TransparentDelegateResuelto();
        [SecurityCritical]
        public delegate void CriticalDelegateResuelto();
        public class TransparentTypeResuelto
        {
            void DelegateBinder()
            {
                TransparentDelegateResuelto td = new TransparentDelegateResuelto(TransparentTarget);
                CriticalDelegate cd = new CriticalDelegate(CriticalTarget);
            }
            void TransparentTarget() { }
            [SecurityCritical]
            void CriticalTarget() { }
        }



        // *****************
        // CODIGO: fxcop:SecurityRuleSetLevel2MethodsShouldNotBeProtectedWithLinkDemands
        // REGLA: CA2135: Level 2 assemblies should not contain LinkDemands
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class MethodsProtectedWithLinkDemandsClass
        {
            // CA2135 violation - the LinkDemand should be removed, and the method marked [SecurityCritical] instead
            [SecurityPermission(SecurityAction.LinkDemand, UnmanagedCode = true)]
            public void ProtectedMethod()
            {
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class MethodsProtectedWithLinkDemandsClassResuelto
        {
            [SecurityCritical]
            public void ProtectedMethod()
            {
            }
        }



        // *****************
        // CODIGO: fxcop:TransparencyAnnotationsShouldNotConflict
        // REGLA: CA2136: Members should not have conflicting transparency annotations
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [SecurityCritical]
        public class CriticalClass
        {
            // CA2136 violation - this method is not really safe critical, since the larger scoped type annotation
            // has precidence over the smaller scoped method annotation.  This can be fixed by removing the
            // SecuritySafeCritical attribute on this method
            [SecuritySafeCritical]
            public void SafeCriticalMethod()
            {
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [SecurityCritical]
        public class CriticalClassResuelto
        {
            public void SafeCriticalMethod()
            {
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsMustBeVerifiable
        // REGLA: CA2137: Transparent methods must contain only verifiable IL
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        // CA2137 violation - transparent method with unverifiable code.  This method should become critical or
        // safe critical 
        public unsafe byte[] UnverifiableMethod(int length)
        {
            byte[] bytes = new byte[length];
            fixed (byte* pb = bytes)
            {
                *pb = (byte)length;
            }

            return bytes;
        }


        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [SecurityCritical]
        public unsafe byte[] UnverifiableMethodResuelto(int length)
        {
            byte[] bytes = new byte[length];
            fixed (byte* pb = bytes)
            {
                *pb = (byte)length;
            }

            return bytes;
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsMustNotCallSuppressUnmanagedCodeSecurityMethods
        // REGLA: CA2138: Transparent methods must not call methods with the SuppressUnmanagedCodeSecurity attribute
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class CallSuppressUnmanagedCodeSecurityClass
        {
            [SuppressUnmanagedCodeSecurity]
            [DllImport("kernel32.dll", SetLastError = true)]
            [return: MarshalAs(UnmanagedType.Bool)]
            static extern bool Beep(uint dwFreq, uint dwDuration);

            public void CallNativeMethod()
            {
                // CA2138 violation - transparent method calling a method marked with SuppressUnmanagedCodeSecurity
                // (this is also a CA2149 violation as well, since this is a P/Invoke and not an interface call).
                Beep(10000, 1);
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class CallSuppressUnmanagedCodeSecurityClassResuelto
        {
            [SecurityCritical]
            [DllImport("kernel32.dll", SetLastError = true)]
            [return: MarshalAs(UnmanagedType.Bool)]
            static extern bool Beep(uint dwFreq, uint dwDuration);

            public void CallNativeMethod()
            {
               Beep(10000, 1);
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsMustNotHandleProcessCorruptingExceptions
        // REGLA: CA2139: Transparent methods may not use the HandleProcessCorruptingExceptions attribute
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class HandleProcessCorruptedStateExceptionClass
        {
            [DllImport("SomeModule.dll")]
            private static extern void NativeCode1();

            // CA2139 violation - transparent method attempting to handle a process corrupting exception
            [HandleProcessCorruptedStateExceptions]
            public void HandleCorruptingExceptions()
            {
                try
                {
                    NativeCode1();
                }
                catch (AccessViolationException) { }
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class HandleProcessCorruptedStateExceptionClassResuelto
        {
            [DllImport("SomeModule.dll")]
            private static extern void NativeCode2();

            [SecurityCritical]
            public void HandleCorruptingExceptions()
            {
                try
                {
                    NativeCode2();
                }
                catch (AccessViolationException) { }
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsMustNotReferenceCriticalCode
        // REGLA: CA2140: Transparent code must not reference security critical items
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [SecurityCritical]
        public class SecurityCriticalClass { }

        public class TransparentMethodsReferenceCriticalCodeClass
        {
            [SecurityCritical]
            private object m_criticalField;

            [SecurityCritical]
            private void CriticalMethod() { }

            public void TransparentMethod()
            {
                // CA2140 violation - transparent method accessing a critical type.  This can be fixed by any of:
                //  1. Make TransparentMethod critical
                //  2. Make TransparentMethod safe critical
                //  3. Make CriticalClass safe critical
                //  4. Make CriticalClass transparent
                List<SecurityCriticalClass> l = new List<SecurityCriticalClass>();

                // CA2140 violation - transparent method accessing a critical field.  This can be fixed by any of:
                //  1. Make TransparentMethod critical
                //  2. Make TransparentMethod safe critical
                //  3. Make m_criticalField safe critical
                //  4. Make m_criticalField transparent
                m_criticalField = l;

                // CA2140 violation - transparent method accessing a critical method.  This can be fixed by any of:
                //  1. Make TransparentMethod critical
                //  2. Make TransparentMethod safe critical
                //  3. Make CriticalMethod safe critical
                //  4. Make CriticalMethod transparent
                CriticalMethod();
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [SecurityCritical]
        public class SecurityCriticalClassResuelto { }

        public class TransparentMethodsReferenceCriticalCodeClassResuelto
        {
            [SecurityCritical]
            private object m_criticalField;

            [SecurityCritical]
            private void CriticalMethod() { }

            [SecurityCritical]
            public void TransparentMethod()
            {
                List<SecurityCriticalClassResuelto> l = new List<SecurityCriticalClassResuelto>();

                m_criticalField = l;

                CriticalMethod();
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsMustNotSatisfyLinkDemands
        // REGLA: CA2141: Transparent methods must not satisfy LinkDemands
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodSatisfiesLinkDemandsClass
        {
            [SecurityPermission(SecurityAction.LinkDemand, UnmanagedCode = true)]
            public void LinkDemandMethod() { }


            public void TransparentMethod()
            {
                // CA2141 violation - transparent method calling a method protected with a link demand.  Any of the
                // following fixes will work here:
                //  1. Make TransparentMethod critical
                //  2. Make TransparentMethod safe critical
                //  3. Remove the LinkDemand from LinkDemandMethod  (In this case, that would be recommended anyway
                //     since it's level 2 -- however you could imagine it in a level 1 assembly)
                LinkDemandMethod();
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodSatisfiesLinkDemandsClassResuelto
        {
            [SecurityPermission(SecurityAction.LinkDemand, UnmanagedCode = true)]
            public void LinkDemandMethod() { }

            [SecurityCritical]
            public void TransparentMethod()
            {
                LinkDemandMethod();
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsShouldNotBeProtectedWithLinkDemands
        // REGLA: CA2142: Transparent code should not be protected with LinkDemands
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodsProtectedWithLinkDemandsClass
        {
            // CA2142 violation - transparent code using a LinkDemand.  This can be fixed by removing the LinkDemand
            // from the method.
            [PermissionSet(SecurityAction.LinkDemand, Unrestricted = true)]
            public void TransparentMethod()
            {
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodsProtectedWithLinkDemandsClassResuelto
        {
            public void TransparentMethod()
            {
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsShouldNotDemand
        // REGLA: CA2143: Transparent methods should not use security demands
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodDemandClass
        {
            // CA2143 violation - transparent code using a Demand.  This can be fixed by making the method safe critical.
            [PermissionSet(SecurityAction.Demand, Unrestricted = true)]
            public void TransparentMethod()
            {
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodDemandClassResuelto
        {
            [SecuritySafeCritical]
            [PermissionSet(SecurityAction.Demand, Unrestricted = true)]
            public void TransparentMethod()
            {
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsShouldNotLoadAssembliesFromByteArrays
        // REGLA: CA2144: Transparent code should not load assemblies from byte arrays
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void TransparentMethod()
        {
            byte[] assemblyBytes = File.ReadAllBytes("DependentAssembly.dll");

            // CA2144 violation - transparent code loading an assembly via byte array.  The fix here is to
            // either make TransparentMethod critical or safe-critical.
            Assembly dependent = Assembly.Load(assemblyBytes);
        }


        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [SecurityCritical]
        public void TransparentMethodResuelto()
        {
            byte[] assemblyBytes = File.ReadAllBytes("DependentAssembly.dll");

            Assembly dependent = Assembly.Load(assemblyBytes);
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsShouldNotUseSuppressUnmanagedCodeSecurity
        // REGLA: CA2145: Transparent methods should not be decorated with the SuppressUnmanagedCodeSecurityAttribute
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class SafeNativeMethod2
        {
            // CA2145 violation - transparent method marked SuppressUnmanagedCodeSecurity.  This should be fixed by
            // marking this method SecurityCritical.
            [DllImport("kernel32.dll", SetLastError = true)]
            [SuppressUnmanagedCodeSecurity]
            [return: MarshalAs(UnmanagedType.Bool)]
            internal static extern bool Beep(uint dwFreq, uint dwDuration);
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class SafeNativeMethods2Resuelto
        {
            [DllImport("kernel32.dll", SetLastError = true)]
            [SecurityCritical]
            [return: MarshalAs(UnmanagedType.Bool)]
            internal static extern bool Beep(uint dwFreq, uint dwDuration);
        }



        // *****************
        // CODIGO: fxcop:TypesMustBeAtLeastAsCriticalAsBaseTypes
        // REGLA: CA2146: Types must be at least as critical as their base types and interfaces
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [SecuritySafeCritical]
        public class SafeCriticalBase
        {
        }

        // CA2156 violation - a transparent type cannot derive from a safe critical type.  The fix is any of:
        //   1. Make SafeCriticalBase transparent
        //   2. Make TransparentFromSafeCritical safe critical
        //   3. Make TransparentFromSafeCritical critical
        public class TransparentFromSafeCritical : SafeCriticalBase
        {
        }

        [SecurityCritical]
        public class CriticalBase
        {
        }

        // CA2156 violation - a transparent type cannot derive from a critical type.  The fix is any of:
        //   1. Make CriticalBase transparent
        //   2. Make TransparentFromCritical critical
        public class TransparentFromCritical : CriticalBase
        {
        }

        // CA2156 violation - a safe critical type cannot derive from a critical type.  The fix is any of:
        //   1. Make CriticalBase transparent
        //   2. Make CriticalBase safe critical
        //   3. Make SafeCriticalFromCritical critical
        [SecuritySafeCritical]
        public class SafeCriticalFromCritical : CriticalBase
        {
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        [SecuritySafeCritical]
        public class SafeCriticalBaseResuelto
        {
        }

        [SecuritySafeCritical]
        public class TransparentFromSafeCriticalResuelto : SafeCriticalBaseResuelto
        {
        }

        [SecurityCritical]
        public class CriticalBaseResuelto
        {
        }

        [SecurityCritical]
        public class TransparentFromCriticalResuelto : CriticalBaseResuelto
        {
        }

        [SecurityCritical]
        public class SafeCriticalFromCriticalResuelto : CriticalBaseResuelto
        {
        }



        // *****************
        // CODIGO: fxcop:SecurityTransparentCodeShouldNotAssert
        // REGLA: CA2147: Transparent methods may not use security asserts
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodsUseSecurityAssertsClass
        {
            // CA2147 violation - transparent code using a security assert declaratively.  This can be fixed by
            // any of:
            //   1. Make DeclarativeAssert critical
            //   2. Make DeclarativeAssert safe critical
            //   3. Remove the assert attribute
            [PermissionSet(SecurityAction.Assert, Unrestricted = true)]
            public void DeclarativeAssert()
            {
            }

            public void ImperativeAssert()
            {
                // CA2147 violation - transparent code using a security assert imperatively.  This can be fixed by
                // any of:
                //   1. Make ImperativeAssert critical
                //   2. Make ImperativeAssert safe critical
                //   3. Remove the assert call
                new PermissionSet(PermissionState.Unrestricted).Assert();
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class TransparentMethodsUseSecurityAssertsClassResuelto
        {
            public void DeclarativeAssert()
            {
            }

            [SecuritySafeCritical]
            public void ImperativeAssert()
            {
                new PermissionSet(PermissionState.Unrestricted).Assert();
            }
        }



        // *****************
        // CODIGO: fxcop:TransparentMethodsMustNotCallNativeCode
        // REGLA: CA2149: Transparent methods must not call into native code
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class CallNativeCodeClass
        {
            [DllImport("kernel32.dll", SetLastError = true)]
            [return: MarshalAs(UnmanagedType.Bool)]
            static extern bool Beep(uint dwFreq, uint dwDuration);

            public void CallNativeMethod()
            {
                // CA2149 violation - transparent method calling native code
                Beep(10000, 1);
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class CallNativeCodeClassResuelto
        {
            [DllImport("kernel32.dll", SetLastError = true)]
            [return: MarshalAs(UnmanagedType.Bool)]
            static extern bool Beep(uint dwFreq, uint dwDuration);

            [SecuritySafeCritical]
            public void CallNativeMethod()
            {
                Beep(10000, 1);
            }
        }



        // *****************
        // CODIGO: fxcop:CA2151
        // REGLA: CA2151: Fields with critical types should be security critical
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        [assembly: AllowPartiallyTrustedCallers]
        [SecurityCritical]
        class Type1 { } // Security Critical type
        class Type2 // Security transparent type
        {
            Type1 m_field; // CA2151, transparent field of critical type
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO 1
        /// </summary>
        [assembly: AllowPartiallyTrustedCallers]
        [SecurityCritical]
        class Type1Resuelto1 { } // Security Critical type
        class Type2Resuelto1 // Security transparent type
        {
            [SecurityCritical]
            Type1Resuelto1 m_field; // Fixed: critical type, critical field
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO 2
        /// </summary>
        [assembly: AllowPartiallyTrustedCallers]
        class Type1Resuelto2 { } // Type1Resuelto2 is now transparent
        class Type2Resuelto2 // Security transparent type
        {
            Type1Resuelto2 m_field; // Fixed: transparent field of transparent type
        }



        // *****************
        // CODIGO: fxcop:NonConstantFieldsShouldNotBeVisible
        // REGLA: CA2211: Non-constant fields should not be visible
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        static public DateTime publicField = DateTime.Now;

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public static readonly DateTime literalField = DateTime.Now;



        // *****************
        // CODIGO: fxcop:CollectionPropertiesShouldBeReadOnly
        // REGLA: CA2227: Collection properties should be read only
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class WritableCollection
        {
            ArrayList strings;

            public ArrayList SomeStrings
            {
                get { return strings; }

                // Violates the rule.
                set { strings = value; }
            }

            public WritableCollection()
            {
                strings = new ArrayList(
                   new string[] { "IEnumerable", "ICollection", "IList" });
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public class WritableCollectionResuelto
        {
            ArrayList strings;

            public ArrayList SomeStrings
            {
                get { return strings; }
            }

            public WritableCollectionResuelto()
            {
                strings = new ArrayList(
                   new string[] { "IEnumerable", "ICollection", "IList" });
            }
        }



        // *****************
        // CODIGO: fxcop:PassSystemUriObjectsInsteadOfStrings
        // REGLA: CA2234: Pass System.Uri objects instead of strings
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        class History
        {
            internal void AddToHistory(string uriString) { }
        }

        public class Browser
        {
            History uriHistory = new History();

            public void AddToHistory()
            {
                uriHistory.AddToHistory("http://www.adventure-works.com");
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        class HistoryResuelto
        {
            internal void AddToHistory(Uri uriType) { }
        }

        public class BrowserResuelto
        {
            HistoryResuelto uriHistory = new HistoryResuelto();

            public void AddToHistory()
            {
                try
                {
                    Uri newUri = new Uri("http://www.adventure-works.com");
                    uriHistory.AddToHistory(newUri);
                }
                catch (UriFormatException uriException) { }
            }
        }



        // *****************
        // CODIGO: fxcop:PInvokesShouldNotBeSafeCriticalFxCopRule
        // REGLA: CA5122: P/Invoke declarations should not be safe critical
        // SEVERIDAD: Informativa
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public class C
        {
            [SecuritySafeCritical]
            [DllImport("kernel32.dll")]
            public static extern bool Beep(int frequency, int duration); // CA5122 – safe critical p/invoke
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        class CResuelto
        {
           [SecurityCritical]
           [DllImport("kernel32.dll", EntryPoint="Beep")]
           private static extern bool BeepPinvoke(int frequency, int duration); // Security Critical P/Invoke
           [SecuritySafeCritical]
           public static bool Beep(int frequency, int duration)
           {
              return BeepPinvoke(frequency, duration);
           }
        }



        // *****************
        // CODIGO: csharpsquid:S2228
        // REGLA: Console logging should not be used
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        private void DoSomething()
        {
            // ...
            Console.WriteLine("so far, so good..."); // Noncompliant
            // ...
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        private void DoSomethingResuelto()
        {
            // ...
            // Eliminar el uso de "Console"
            // ...
        }



        // *****************
        // CODIGO: csharpsquid:S1104
        // REGLA: Fields should not have public accessibility
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public int instanceData = 32;

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        private int instanceDataResuelto = 32;

        public int InstanceDataResuelto
        {
            get { return instanceDataResuelto; }
            set { instanceDataResuelto = value; }
        }



        // *****************
        // CODIGO: csharpsquid:S1313
        // REGLA: IP addresses should not be hardcoded
        // SEVERIDAD: Mayor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void parseIp()
        {
            var ip = "127.0.0.1";
            var address = IPAddress.Parse(ip);
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void parseIpResuelto()
        {
            var ip = ConfigurationManager.AppSettings["myapplication.ip"];
            var address = IPAddress.Parse(ip);
        }



        // *****************
        // CODIGO: csharpsquid:S2386
        // REGLA: Mutable fields should not be "public static"
        // SEVERIDAD: Menor
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public static List<String> strings3 = new List<String>();


        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        private static List<String> strings3Resuelto = new List<String>();

        public List<String> Strings3Resuelto
        {
            get { return new List<String>(strings3Resuelto); }
        }



        // *****************
        // CODIGO: csharpsquid:S2278
        // REGLA: Neither DES (Data Encryption Standard) nor DESede (3DES) should be used
        // SEVERIDAD: Bloqueante
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void encriptar()
        {
            using (var tripleDES = new TripleDESCryptoServiceProvider()) //Noncompliant
            {
                //...
            }
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void encriptarResuelto()
        {
            using (var aes = new AesCryptoServiceProvider())
            {
                //...
            }
        }


        // *****************
        // CODIGO: csharpsquid:S2070
        // REGLA: SHA-1 and Message-Digest hash algorithms should not be used
        // SEVERIDAD: Crítica
        // *****************

        /// <summary>
        /// EJEMPLO DE INCUMPLIMIENTO
        /// </summary>
        public void crearHash()
        {
            var hashProvider1 = new MD5CryptoServiceProvider();
            var hashProvider2 = HashAlgorithm.Create("SHA1");
        }

        /// <summary>
        /// RESOLUCIÓN DE INCUMPLIMIENTO
        /// </summary>
        public void crearHashResuelto()
        {
            var hashProvider1 = new SHA256Managed();
            var hashProvider2 = (HashAlgorithm)CryptoConfig.CreateFromName("SHA256Managed");
        }
        




    }
}
