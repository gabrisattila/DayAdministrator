Option Explicit

On Error Resume Next

ExcelMacroExample

Sub ExcelMacroExample()

    Dim xlApp
    Dim xlBook

    Set xlApp = CreateObject("Excel.Application")
    Set xlBook = xlApp.Workbooks.Open("C:\Users\gabri\Desktop\JOB\own\The_Time_Proba.xlsm", 0, True)
    xlApp.Run "Workbook_Open"
    xlApp.Quit

    Set xlBook = Nothing
    Set xlApp = Nothing

End Sub