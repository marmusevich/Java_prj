package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 */
public class UnknownCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(UnknownCommand.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
        result.add("Unknown command");
    }
}

//
//LOGIN='SYSDBA';
//        PASSWD='qazwsx12';
//
////DB='91.197.16.103:TERMINAL';
////DB_WORK='91.197.16.103:WORKING';
//
//        DB='192.168.0.202:TERMINAL';
//        DB_WORK='192.168.0.202:WORKING';
////DB_WORK='192.168.0.202:PLATEGKA';
//        var
//        Results, LCmd:string;
//        Str:TStringList;
//        counts:integer;
//        scrstream: TMemoryStream;
//        begin
//        PATHPRG:=ExtractFilePath(Application.ExeName);
//        Str:=TStringList.Create;
//
//        with AContext.Connection do
//        try
//        section.Enter;
//        Inc(processed, 1);
//        try
//        LCmd := Trim(Socket.ReadLn(TEncoding.UTF8));
////*///////////////////////////////////////////////////////////////////////////
//        //AContext.Connection.IOHandler.
//        if SameText(trim(LCmd), 'process') then
//        begin
//        Socket.WriteLn('Connect: '+IntToStr(processed),TEncoding.UTF8);
//        end
//
////*///////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'quit') then
//        begin
//        Socket.WriteLn('200 Good bye',TEncoding.UTF8);
//        end
/////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'seterrormsg') then
//        begin
//        AContext.Connection.Socket.WriteLn('SETERRORMSG');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.SetErrorMsg(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        end
////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getcomponent') then
//        begin
//        Socket.WriteLn('GCOMPONENT',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn,1);
//        Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetComponent(Str,Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        end
//////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'chkupdate') then
//        begin
//        Socket.WriteLn('CHKUPDATE',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn,1);
//        Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        results:=DM1.ChkUpdate(Str, Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        end
//
////*/////////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getaddres') then
//        begin
//        AContext.Connection.Socket.WriteLn('GADDRES',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,3,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        Results:=DM1.GetAddres(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        //AContext.Connection.Socket.Close;
//        end
////Пересылка файла по стриму
//        else if SameText(trim(LCmd), 'updateprg') then
//        begin
//        AContext.Connection.Socket.WriteLn('UPDATEPRG',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        scrStream:=TMemoryStream.Create;
//        try
//        if Str.Values['PATHPRG']='' then Str.Values['PATHPRG']:=PATHPRG;
//        scrStream.LoadFromFile(trim(Str.Values['PATHPRG'])+'\'+trim(STR.Values['FILENAME']));
//        AContext.Connection.Socket.WriteLn(IntToStr(scrstream.Size));
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(scrstream);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        except
//        AContext.Connection.Socket.WriteLn('500 ERROR TRANSFER',TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        Str.SaveToFile('C:\ERROR TRANSFER.txt');
//        end;
//        scrstream.Free;
//        end
//
////*//////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'date') then
//        begin
//        AContext.Connection.Socket.WriteLn('200 ' + FormatDateTime('dd.MM.yyyy hh:mm:ss',Now),TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getkomisiya') then
//        begin
//        AContext.Connection.Socket.WriteLn('GKOMISIA',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetKomisiya(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        end;
//        //AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getsostav') then
//        begin
//        AContext.Connection.Socket.WriteLn('GSOSTAV',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,4,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetSostav(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,true,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        end;
//        AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setcurrence') then
//        begin
//        AContext.Connection.Socket.WriteLn('SETCUR',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,4,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        AContext.Connection.Socket.WriteLn(DM1.SetCurrence(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB));
//        //AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setstorno') then
//        begin
//        AContext.Connection.Socket.WriteLn('SETSTORNO',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        AContext.Connection.Socket.WriteLn(DM1.SetStorno(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB));
//        //AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setdata') then
//        begin
//        AContext.Connection.Socket.WriteLn('WD');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.SetData(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        //Вренем PAY_ID для чека
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(GET_USLUGA.Strings[0],TEncoding.UTF8);
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setcounter') then
//        begin
//        try
//        Socket.WriteLn('WCOUNTER');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.SetCounter(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        end;
//        except
//        on e: EIdException do
//        begin
//        Str.Add('Error '+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_EIdException_setCounter.txt');
//        end; // on EIdException
//        on e: Exception do
//        begin
//        Str.Add('Error: '+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_Exception_setCounter.txt');
//        end; // on Exception
//        end;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getsmena') then
//        begin
//        AContext.Connection.Socket.WriteLn('GSMENA');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetSmena(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getoplata') then
//        begin
//        AContext.Connection.Socket.WriteLn('GOPLATA');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetOplata(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
///////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getoplatals') then
//        begin
//        AContext.Connection.Socket.WriteLn('GOPLATALS');
//        try
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        except
//        counts := 0;
//        end;
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        try
//        Results:=DM1.GetOplataLS(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        except
//        AContext.Connection.Socket.WriteLn('500 ERROR',TEncoding.UTF8);
//
//        end;
//        if (Results = '200 OK') and (GET_USLUGA.Count>0) then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        AContext.Connection.Socket.WriteLn('400 None records',TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        AContext.Connection.Disconnect;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getoplatasmena') then
//        begin
//        AContext.Connection.Socket.WriteLn('GOPLATASMENA');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetOplataSmena(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getcounter') then
//        begin
//        AContext.Connection.Socket.WriteLn('GCOUNTER');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetCounter(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        //AContext.Connection.Socket.Close;
//        end
//
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getreestr') then
//        begin
//        AContext.Connection.Socket.WriteLn('GREESTR');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetReestr(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getreportlist') then
//        begin
//        AContext.Connection.Socket.WriteLn('GREPORTLIST');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetReportList(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'startoplata') then
//        begin
//        AContext.Connection.Socket.WriteLn('STARTOPL',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
////    AContext.Connection.Socket.WriteLn(
//        Results:=DM1.StartOplata(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'closeoplata') then
//        begin
//        AContext.Connection.Socket.WriteLn('CLOSEOPL',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        AContext.Connection.Socket.WriteLn(DM1.CloseOplata(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB),TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end
/////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'stopsmen') then
//        begin
//        AContext.Connection.Socket.WriteLn('WSTOP',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        Results:=DM1.CloseSmen(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results='200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,true,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else  begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
////    AContext.Connection.Disconnect;
//        end
//////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'startsmen') then
//        begin
//        AContext.Connection.Socket.WriteLn('WSTART',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteLn(DM1.StartSmen(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB),TEncoding.UTF8);
////    AContext.Connection.Socket.WriteLn('200 OK');
//        end
////*//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'gettable') then
//        begin
//        AContext.Connection.Socket.WriteLn('GTABLE',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,3,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        if (STR.VALUES['TABLE']='STREET') or
//        (STR.VALUES['TABLE']='CITY') or
//        (STR.VALUES['TABLE']='BANK') or
//        (STR.VALUES['TABLE']='FILIAL') or
//        (STR.VALUES['TABLE']='VIDUSLUGI') or
//        (STR.VALUES['TABLE']='ORGANIZATION') or
//        (STR.VALUES['TABLE']='TIP_ORGANIZATION') or
//        (STR.VALUES['TABLE']='TIP_STREET') or
//        (STR.VALUES['TABLE']='KOD_OPLAT') or
//        (STR.VALUES['TABLE']='SOSTAVUSLUG') or
//        (STR.VALUES['TABLE']='TERMINAL')
//        then
//        Results:=DM1.GetTable(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        //AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getdata') then
//        begin
//        AContext.Connection.Socket.WriteLn('GDATA',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),0);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        // AContext.Connection.Disconnect;
//        Results:=DM1.GetData(Str,'0',LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getdatafull') then
//        begin
//        AContext.Connection.Socket.WriteLn('GDATAFULL',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),0);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetDataFull(Str,'0',LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        //  AContext.Connection.Socket.Close;
//        end
////*///////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'findadr') then
//        begin
//        AContext.Connection.Socket.WriteLn('FINDADR',TEncoding.UTF8);
//        //прочитаем: код города, код улицы, номер дома, корпус, квартиру.
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetFindAdr(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK');
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
////*///////////////////////////////////////////////////////////////////////
//        //прочитаем: код города, код улицы, номер дома, корпус, квартиру.
//        else if SameText(trim(LCmd), 'lsfindadr') then
//        begin
//        AContext.Connection.Socket.WriteLn('LSFINDADR',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetLSFindAdr(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK');
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
////*//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'findls') then
//        begin
//        AContext.Connection.Socket.WriteLn('FINDLS',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,3,TEncoding.UTF8);
//        Results:=DM1.GetFindLS(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        end
//////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getaddrterm') then
//        begin
//        AContext.Connection.Socket.WriteLn('GADDRTRM');
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetAddrTerm(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(GET_USLUGA.Strings[0],TEncoding.UTF8);
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
//        //////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'cheksmena') then
//        begin
//        AContext.Connection.Socket.WriteLn('CHKSMENA');
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.ChekSmena(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        AContext.Connection.Socket.WriteLn(Results);
//        AContext.Connection.Socket.Close;
//        end
////////////////////////////////////////////////////////////////////////////
//        else
//        begin
//        AContext.Connection.Socket.WriteLn('Error command :(',TEncoding.UTF8);
//        end;
//        except
//        {on e: EIdException do
//        begin
//        AContext.Connection.Socket.Close;
//        AContext.Connection.Disconnect;
//
//        Str.Add('Error: '+#13+Str.Values['ID_TERMINAL']+#13+Str.Values['LOGIN']+#13+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_EIdException.txt');
//        end; // on EIdException
//        on e: Exception do
//        begin
//        Str.Add('Error: '+#13+Str.Values['ID_TERMINAL']+#13+Str.Values['LOGIN']+#13+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_Exception.txt');
//        end; // on Exception  }
//        end;
//        finally
//        section.Leave;
//        Disconnect;
//        end;
//        Str.Free;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//////////////////Общие фцнкции
//        procedure TDM1.ErrorHandler1FIBErrorEvent(Sender: TObject;
//        ErrorValue: EFIBError; KindIBError: TKindIBError; var DoRaise: Boolean);
//        var
//        STR:TStringList;
//        Lasterror: string;
//        FKindIBError: string;
//        begin
//        STR:=TStringList.Create;
//        STR.Add(#13#10 + '===== ErrorHandler FIBErrorEvent =====');
//        STR.Add('Sender.ClassName = ' + Sender.ClassName);
//        STR.Add('Sender.Name = ' + (Sender as TComponent).Name);
//        if Sender is TFIBQuery then
//        STR.Add('Owner.Name = ' +
//        (Sender as TFIBQuery).Owner.Name);
//        if Sender is TpFIBStoredProc then
//        STR.Add('Sender.StoredProcName = ' +
//        (Sender as TpFIBStoredProc).StoredProcName);
//        STR.Add('ConstraintName = ' +
//        ErrorHandler1.ConstraintName);
//        STR.Add('ExceptionNumber = ' +
//        IntToStr(ErrorHandler1.ExceptionNumber));
//        case ErrorHandler1.LastError of
//        keNoError: Lasterror := 'keNoError';
//        keException: Lasterror := 'keException';
//        keForeignKey: Lasterror := 'keForeignKey';
//        keSecurity: Lasterror := 'keSecurity';
//        keLostConnect: Lasterror := 'keLostConnect';
//        keCheck: Lasterror := 'keCheck';
//        keUniqueViolation: Lasterror := 'keUniqueViolation';
//        keOther: Lasterror := 'keOther';
//        else
//        Lasterror := 'Undefined';
//        end;
//        STR.Add('Lasterror = ' + Lasterror);
//        STR.Add('SQLCode = ' + IntToStr(ErrorValue.SQLCode));
//        STR.Add('IBErrorCode = ' +
//        IntToStr(ErrorValue.IBErrorCode));
//        STR.Add('Message = ' + ErrorValue.Message);
//        STR.Add('IBMessage = ' + ErrorValue.IBMessage);
//        STR.Add('SQLMessage = ' + ErrorValue.SQLMessage);
//        case KindIBError of
//        keNoError: FKindIBError := 'keNoError';
//        keException: FKindIBError := 'keException';
//        keForeignKey: FKindIBError := 'keForeignKey';
//        keSecurity: FKindIBError := 'keSecurity';
//        keLostConnect: Lasterror := 'keLostConnect';
//        keCheck: FKindIBError := 'keCheck';
//        keUniqueViolation: FKindIBError := 'keUniqueViolation';
//        keOther: FKindIBError := 'keOther';
//        else
//        FKindIBError := 'Undefined';
//        end;
//        STR.Add('KindIBError = ' + FKindIBError);
//        STR.SaveToFile('C:\KindIBError.txt');
//        STR.Free;
////  DoRaise := False;
//        end;
//
//        function TDM1.GetTermnalID(ID_TERMINAL: string):integer;
//        begin
//        Result:=0;
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ID FROM TERMINAL WHERE terminal_id=:ID_TERMINAL';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=ID_TERMINAL;
//        sqlFreeReturn.Open;
//        Result:=sqlFreeReturn.FieldByName('ID').asInteger;
//        end;
//
//        function TDM1.ConnectFIB(USER:string;PASSWD:string;DB:string):boolean;
//        begin
//        FIB.Connected:=false;
//        FIB.DBParams.Clear;
//        FIB.DBParams.Add('user_name='+USER);
//        FIB.DBParams.Add('password='+PASSWD);
//        FIB.DBParams.Add('lc_ctype=WIN1251');
//        FIB.DBName:=DB;
//        try
//        FIB.Connected:=true;
//        FIBTransaction.Active:=true;
//        //Определения вывода значений даты и времени
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        Result:=true;
//        except
//        Result:=false;
//        end;
//        end;
//
//        function TDM1.ConnectFIBWORK(USER:string;PASSWD:string;DB_WORK:string):boolean;
//        begin
//        FIBWORKS.Connected:=false;
//        FIBWORKS.DBParams.Clear;
//        FIBWORKS.DBParams.Add('user_name='+USER);
//        FIBWORKS.DBParams.Add('password='+PASSWD);
//        FIBWORKS.DBParams.Add('lc_ctype=WIN1251');
//        FIBWORKS.DBName:=DB_WORK;
//        try
//        FIBWORKS.Connected:=true;
//        FIBTransaction_working.Active:=true;
//        //Определения вывода значений даты и времени
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        Result:=true;
//        except
//        Result:=false;
//        end;
//        end;
//
////Получает ID терминала с проверкой на открытую смену
//        function TDM1.TerminalID(DATA:TStringList):integer;
//        begin
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ADDRES,ID, BANK_ID FROM TERMINAL WHERE TERMINAL_ID=:ID_TERMINAL AND BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN=:LOGIN) AND (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=DATA.VALUES['ID_TERMINAL'];
//        sqlFreeReturn.ParamByName('LOGIN').asString:=DATA.VALUES['LOGIN'];
//        try
//        sqlFreeReturn.Open;
//        //Определения вывода значений даты и времени
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        Result:=sqlFreeReturn.FieldByName('ID').asInteger;
//        except
//        Result:=0
//        end;
//        end;
/////////////////////////////////////////////////////////////
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
////Добавление записи в базу ошибок
//        function TDM1.SetErrorMsg(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        ID_TERMINAL, i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        Str:=TStringList.Create;
//        try
//        ID_TERMINAL:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//        //Определим значение генератора пачек денег кешкодера
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO TERMINAL_ERRORS (ID_TERMINAL ,ERROR_MSG)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:ID_TERMINAL, :ERROR_MSG)');
//        sqlNew.ParamByName('ID_TERMINAL').AsInteger:=ID_TERMINAL;
//        sqlNew.ParamByName('ERROR_MSG').asString:=trim(DATA.VALUES['ERROR_MSG']);
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        Result:='200 OK';
//        sqlNew.Transaction.CommitRetaining;
//        except
//        sqlNew.Transaction.RollbackRetaining;
//        Str.Add('except errors params');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        //***************************************************************
//        Str.Free;
//        END ELSE Result:='500 Error connet to FIB'; //Проверка подключения к базе
//
//        end;
//
//
////Возвращает перечень разрешенных компонентов программы
//        function TDM1.GetComponent(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        ID_TERMINAL:integer;
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        //Получим внутренний ID терминала
//        ID_TERMINAL:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//
//        sqlNew.SQL.Add('SELECT * from COMPONENT where ID_TERMINAL=:ID_TERMINAL');
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=ID_TERMINAL;
//
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        while not sqlNew.EOF do
//        begin
//        GET_USLUGA.Add(Trim(sqlNew.FieldByName('ID_COMPONENT').asString));
//        sqlNew.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        sqlNew.SQL.SaveToFile('C:\ErrorComponent.txt');
//        Result:='500 Error COMPONENT';
//        exit;
//        end;
//
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//
//        function ChkVersion(old,New:string):boolean;
//        var
//        Major,Minor,Release,Build:string;
//        MajorN,MinorN,ReleaseN,BuildN:string;
//        S:string;
//        begin
//        Result:=false;
//        if (old>'')and (Length(old)>6) then
//        begin
//        ///////
//        S:=old;
//        Delete(S,pos('.',S),255);
//        Major:=S;
//        //////
//        S:=old;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,pos('.',S),255); //Второе значение
//        Minor:=S;
//        //////
//        S:=old;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,pos('.',S),255); //Третье значение
//        Release:=S;
//        //////
//        S:=old;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,1,pos('.',S));//Третий символ до точки
//        Delete(S,pos('.',S),255); //Четвертое значение
//        Build:=S;
//        end;
//        if (new>'') and (Length(new)>6) then
//        begin
//        S:=new;
//        Delete(S,pos('.',S),255);
//        MajorN:=S;
//        //////
//        S:=new;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,pos('.',S),255); //Второе значение
//        MinorN:=S;
//        //////
//        S:=new;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,pos('.',S),255); //Третье значение
//        ReleaseN:=S;
//        //////
//        S:=new;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,1,pos('.',S));//Третий символ до точки
//        Delete(S,pos('.',S),255); //Четвертое значение
//        BuildN:=S;
//        end;
//        if StrToIntDef(MajorN,0)>StrToIntDef(Major,0) then Result:=true
//        else
//        if StrToIntDef(MinorN,0)>StrToIntDef(Minor,0) then Result:=true
//        else
//        if StrToIntDef(ReleaseN,0)>StrToIntDef(Release,0) then Result:=true
//        else
//        if StrToIntDef(BuildN,0)>StrToIntDef(Build,0) then Result:=true
//        else
//        Result:=false;
//        end;
//
////Проверка наличия обновлений
//        function TDM1.ChkUpdate(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        Versions:TCEVersionInfo;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
//        //BEGIN
//        //Создадим запрос поиска имени файла по логину и паролю пользователя
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT PATCH, filename from prg_version where');
//        sqlFree.SelectSQL.Add('id=(select prg_version from USERS where USERS.login=:LOGIN)');
//        sqlFree.ParamByName('LOGIN').asString:=DATA.Values['LOGIN'];
//        try
//        sqlFree.Open;
//        GET_USLUGA:=TStringList.Create;
//        except
//        sqlFree.SelectSQL.SaveToFile('C:\ErrorCHKUPDATE.txt');
//        Result:='500 Error CHKUPDATE';
//        Exit;
//        end;
//        Versions:=TCEVersionInfo.Create(Owner);
//        while not sqlFree.EOF do
//        begin
//        //Versions.GetInfo(sqlFree.FieldByName('PATCH').asString+sqlFree.FieldByName('filename').asString);
//        Versions.GetInfo(sqlFree.FieldByName('PATCH').asString+trim(DATA.VALUES['FILENAME']));
//
//        GET_USLUGA.Add('PATCH='+sqlFree.FieldByName('PATCH').asString+trim(DATA.VALUES['FILENAME']));
//        GET_USLUGA.Add('VERSIONS='+Versions.FileVersion);
//        GET_USLUGA.Add('VERSIONL='+trim(DATA.Values['VERSIONL']));
//        GET_USLUGA.Add('FILENAME='+trim(DATA.VALUES['FILENAME']));
//        if ChkVersion(trim(DATA.Values['VERSIONL']),Versions.FileVersion) then
//        GET_USLUGA.Add('UPDATE=true') else GET_USLUGA.Add('UPDATE=false');
//        sqlFree.Next;
//        end;
//        Versions.Free;
//        Result:='200 OK';
//        Exit;
//        //END ELSE BEGIN Result:='500 Not open smena';  Exit; END;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//        procedure AddText(Txt: String);
//        const
//        log = 'C:\service_test.log';
//        var
//        TF: TextFile;
//        begin
//        AssignFile(TF, log);
//        try
//        if FileExists(log) then Append(TF)
//        else ReWrite(TF);
//        WriteLn(TF, Txt);
//        finally
//        Flush(TF);
//        CloseFile(TF);
//        end;
//        end;
//
////Функция проверки на правильность даты строковой переменной
//        function IsDate(str: string): Boolean;
//        var
//        dt: TDateTime;
//        begin
//        Result := True;
//        try
//        dt := StrToDateTime(str);
//        except
//        Result := False;
//        end;
//        end;
//
//        {//////////////////////////////Замена подстроки/////////////////////////////////////////}
//        function ReplaceSub(str, sub1, sub2: string): string;
//        var
//        aPos: Integer;
//        rslt: string;
//        begin
//        aPos := Pos(sub1, str);
//        rslt := '';
//        while (aPos <> 0) do
//        begin
//        rslt := rslt + Copy(str, 1, aPos - 1) + sub2;
//        Delete(str, 1, aPos + Length(sub1) - 1);
//        aPos := Pos(sub1, str);
//        end;
//        Result := rslt + str;
//        end;
//
//        {//////////////////////////////////////////////////////////////////////////////////////}
////Закрытие смены
//        function TDM1.CloseSmen(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        SMENA,ID_TERMINAL,INKASATOR:INTEGER;
//        SUMMA:extended;
//        DATA_K,DATA_N:TDateTime;
//        begin
//        Result:='500 ERROR';
//        IF ConnectFIB(USER,PASSWD,DB) THEN
//        BEGIN
//
//        ID_TERMINAL:=GetTermnalID(
//        DATA.Values['ID_TERMINAL']);
//
//        //Получим ID инкасатора
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ID FROM INKASATOR WHERE KEY_ID=:KEY_ID';
//        sqlFreeReturn.ParamByName('KEY_ID').asString:=trim(DATA.Values['LOGIN']);
//        sqlFreeReturn.Open;
//        INKASATOR:=sqlFreeReturn.FieldByName('ID').asInteger;
//
//        //Необходимо узнать номер текущей смены для данного терминала
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ID, DATA_K, DATA_N FROM SMENA WHERE ID_TERMINAL=:ID_TERMINAL and DATA_K is null';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asInteger:=ID_TERMINAL;
//        sqlFreeReturn.Open;
//        SMENA:=sqlFreeReturn.FieldByNAme('ID').asInteger;
//        DATA_K:=sqlFreeReturn.FieldByNAme('DATA_K').AsDateTime;
//        DATA_N:=sqlFreeReturn.FieldByNAme('DATA_N').AsDateTime;
//
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT SUM(SUMMA) FROM OPLATA WHERE KOD_SMEN=:KOD_SMEN';
//        sqlFreeReturn.ParamByName('KOD_SMEN').asInteger:=SMENA;
//        sqlFreeReturn.Open;
//        SUMMA:=sqlFreeReturn.FieldByNAme('SUM').asFloat;
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('UPDATE SMENA set DATA_K = :DATA_K, INKASATOR=:INKASATOR WHERE ID_Terminal=:ID_TERMINAL and DATA_K is null');
//        sqlNew.ParamByName('DATA_K').AsDateTime:=Now();
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=ID_TERMINAL;
//        sqlNew.ParamByName('INKASATOR').asInteger:=INKASATOR;
//        try
//        GET_USLUGA:=TStringList.Create;
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//
//        GET_USLUGA.Add('SUMMA='+FloatToStr(SUMMA));
//        GET_USLUGA.Add('SMENA='+IntToStr(SMENA));
//        GET_USLUGA.Add('DATA_K='+FormatDateTime('dd.mm.yyyy hh.mm.ss',DATA_K));
//        GET_USLUGA.Add('DATA_N='+FormatDateTime('dd.mm.yyyy hh.mm.ss',DATA_N));
//
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='select nominal, count(nominal) kolvo from currency  where  currency.smena=:smena group by nominal';
//        sqlFreeReturn.ParamByName('SMENA').asInteger:=SMENA;
//        sqlFreeReturn.Open;
//        while not sqlFreeReturn.EOF do
//        begin
//        GET_USLUGA.Add(sqlFreeReturn.FieldByName('nominal').asString+'='+sqlFreeReturn.FieldByName('kolvo').asString);
//        sqlFreeReturn.Next;
//        end;
//        Result:='200 OK';
//        except
//        Result:='500 Error Insert record';
//        Exit;
//        end;
//        END ELSE Result:='Error connect FIB';
//        end;
//
//////////////////////////////////////////////////////////////////////////////////////////
//        function TDM1.ChekSmena(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        begin
//
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Clear;
//        sqlFreeReturn.SelectSQL.Add('SELECT  TERMINAL.ID KASSA_ID,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.terminal_id ,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.addres ADDRES,');
//        sqlFreeReturn.SelectSQL.Add('terminal.NAME TERMINAL,');
//        sqlFreeReturn.SelectSQL.Add('users.login USERS,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.FILIAL_ID,');
//        sqlFreeReturn.SelectSQL.Add('filial.NAME filial,');
//        sqlFreeReturn.SelectSQL.Add('bank.name BANK,');
//        sqlFreeReturn.SelectSQL.Add('bank.id BANK_ID,');
//        sqlFreeReturn.SelectSQL.Add('USERS.FIO,');
//        sqlFreeReturn.SelectSQL.Add('(SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID) SMENA,');
//        sqlFreeReturn.SelectSQL.Add('(SELECT distinct(smena.id) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID) smena_id,');
//        sqlFreeReturn.SelectSQL.Add('(SELECT distinct(smena.data_n) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID) data_n,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.RS_DEBET RS_DEBET,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.RS_CREDIT RS_CREDIT');
//        sqlFreeReturn.SelectSQL.Add('   FROM TERMINAL');
//
//        sqlFreeReturn.SelectSQL.Add(' left join filial on (filial.id=terminal.filial_id)');
//        sqlFreeReturn.SelectSQL.Add(' left join BANK on (BANK.id=terminal.bank_id)');
//        sqlFreeReturn.SelectSQL.Add(' left join users on (USERS.bank=terminal.bank_id and USERS.login=:LOGIN)');
//        sqlFreeReturn.SelectSQL.Add(' WHERE');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.TERMINAL_ID=:ID_TERMINAL');
//        sqlFreeReturn.SelectSQL.Add('AND');
//        sqlFreeReturn.SelectSQL.Add('terminal.BANK_ID =(SELECT USERS.bank FROM USERS WHERE LOGIN=:LOGIN)');
//
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=trim(DATA.Values['ID_TERMINAL']);
//        sqlFreeReturn.ParamByName('LOGIN').AsString:=trim(DATA.Values['LOGIN']);
//        try
//        sqlFreeReturn.Open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeReturn.EOF do
//        begin
//        GET_USLUGA.Add('ADDRES='+trim(sqlFreeReturn.FieldByName('ADDRES').asString));
//        GET_USLUGA.Add('KASSA_ID='+trim(sqlFreeReturn.FieldByName('KASSA_ID').asString));
//        GET_USLUGA.Add('TERMINAL='+trim(sqlFreeReturn.FieldByName('TERMINAL').asString));
//        GET_USLUGA.Add('SMENA='+trim(sqlFreeReturn.FieldByName('SMENA').asString));
//        GET_USLUGA.Add('SMENA_ID='+trim(sqlFreeReturn.FieldByName('SMENA_ID').asString));
//        GET_USLUGA.Add('BANK_NAME='+trim(sqlFreeReturn.FieldByName('BANK').asString));
//        GET_USLUGA.Add('DATA_N='+trim(FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFreeReturn.FieldByName('DATA_N').AsDateTime)));
//        GET_USLUGA.Add('BANK_ID='+trim(sqlFreeReturn.FieldByName('BANK_ID').asString));
//        GET_USLUGA.Add('FILIAL_ID='+trim(sqlFreeReturn.FieldByName('FILIAL_ID').asString));
//        GET_USLUGA.Add('FILIAL='+trim(sqlFreeReturn.FieldByName('FILIAL').asString));
//        GET_USLUGA.Add('FIO='+trim(sqlFreeReturn.FieldByName('FIO').asString));
//        GET_USLUGA.Add('RS_DEBET='+trim(sqlFreeReturn.FieldByName('RS_DEBET').asString));
//        GET_USLUGA.Add('RS_CREDIT='+trim(sqlFreeReturn.FieldByName('RS_CREDIT').asString));
//        sqlFreeReturn.Next;
//        end;
//        Result:='200 OK';
//        except
//        Result:='500 Error open SQL';
//        Exit;
//        end;
//
//        END ELSE result:='500 Error connect FIB';
//
//        end;
//
////Открытие смены
//        function TDM1.StartSmen(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//
//        sqlNew.Close;
//        sqlNew.SQL.Text:='select count(id) COUNTS from smena where id_terminal=:ID_TERMINAL and data_k is null';
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//        sqlNew.ExecQuery;
//
//        if sqlNew.FieldByName('COUNTS').AsInteger=0 then
//        begin
//
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT * FROM TERMINAL WHERE terminal_ID=:ID_TERMINAL and BANK_ID=(SELECT BANK FROM USERS WHERE LOGIN=:LOGIN)';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=trim(DATA.Values['ID_TERMINAL']);
//        sqlFreeReturn.ParamByName('LOGIN').asString:=trim(DATA.Values['LOGIN']);
//        sqlFreeReturn.Open;
//
//
//        if sqlFreeReturn.RecordCount>0 then
//        begin
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO SMENA (DATA_N, ID_TERMINAL) VALUES');
//        sqlNew.SQL.Add('(:DATA_N, :ID_TERMINAL)');
//        sqlNew.ParamByName('DATA_N').AsDateTime:=Now();
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=sqlFreeReturn.FieldByName('ID').asInteger;
//        //sqlNew.ParamByName('ID_LOGIN').asInteger:=ID_LOGIN;
//        //sqlNew.SQL.SaveToFile('C:\123.txt');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';
//        Exit;
//        end;
//        end
//        else begin Result:='500 Users not found'; Exit; end;
//        end else Result:='400  smen opened'; //Конец проверки условия открытой смены
//        END ELSE result:='500 Error connect FIB';
//        end;
//
////Добавление данных счетчика в базу
//        function TDM1.SetCounter(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        id_curr:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        Str:=TStringList.Create;
//        if (TerminalID(DATA)>0)THEN
//        BEGIN //Проверка открытой смены по терминалу
//        try
//        //Определим значение генератора пачек денег кешкодера
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO COUNTER (LS ,DT, USLUGA, IDEN_SHET, POKAZ_PRED, POKAZ_TEK, TARIF, PAY_ID, KOD_ORG)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:LS ,:DT, :USLUGA, :IDEN_SHET, :POKAZ_PRED, :POKAZ_TEK, :TARIF, :PAY_ID, :KOD_ORG)');
//        sqlNew.ParamByName('LS').AsInteger:=StrToIntDef(trim(DATA.VALUES['LS']),0);
//        sqlNew.ParamByName('DT').AsDateTime:=StrToDateTimeDef(trim(DATA.VALUES['DT']),StrToDateTime('01.01.2001 00:00:01'));
//        sqlNew.ParamByName('USLUGA').asInteger:=StrToIntDef(trim(DATA.VALUES['USLUGA']),0);
//        sqlNew.ParamByName('IDEN_SHET').asString:=trim(DATA.VALUES['IDEN_SHET']);
//        sqlNew.ParamByName('POKAZ_TEK').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['POKAZ_TEK']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('POKAZ_PRED').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['POKAZ_PRED']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('TARIF').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['TARIF']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('PAY_ID').AsInteger:=StrToIntDef(trim(DATA.VALUES['PAY_ID']),0);
//        sqlNew.ParamByName('KOD_ORG').AsInteger:=StrToIntDef(trim(DATA.VALUES['KOD_ORG']),0);
//        except
//        Str.Add('except errors params');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        Str.SaveToFile('C:\CountersExcept.txt');
//        end;
//        //***********************************
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        Str.Add(sqlNew.SQL.Text);
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        Str.SaveToFile('C:\ErrorInsertCounter.txt');
//        exit;
//        end;
//        //***************************************************************
//        END ELSE Result:='500 Error open smen';  //Открытая смена по терминалу
//        Str.Free;
//        END ELSE Result:='500 Error connet to FIB'; //Проверка подключения к базе
//
//        end;
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
////Добавление платежа в оплату
//        function TDM1.SetData(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        id_curr:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        Str:=TStringList.Create;
//        if (TerminalID(DATA)>0)THEN
//        BEGIN //Проверка открытой смены по терминалу
//        try
//        //Определим значение генератора пачек денег кешкодера
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO OPLATA (ID_CURRENCE ,ADDRES, FIO, SUMMA, INN, POSTANOVA, DATA_POSTANOV, KOD_ORG, ID_TERMINAL, USLUGA, DATA, IP, KTO_V, PER_START, PER_END, LS, LS_POLUCH, KOD_ORG_DOP, KOMISIYA, KOMISIYA_BANK, ORGANIZATION, MFO, OKPO, BANK, R_SHET)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:ID_CURRENCE, :ADDRES, :FIO, :SUMMA, :INN, :POSTANOVA, :DATA_POSTANOV, :KOD_ORG, :ID_TERMINAL, :USLUGA, :DATA, :IP, :KTO_V, :PER_START, :PER_END, :LS, :LS_POLUCH, :KOD_ORG_DOP, :KOMISIYA, :KOMISIYA_BANK, :ORGANIZATION, :MFO, :OKPO, :BANK, :R_SHET)');
//        sqlNew.ParamByName('ID_CURRENCE').AsInt64:=StrToInt64Def(trim(DATA.VALUES['ID_CURRENCE']),0);
//        sqlNew.ParamByName('ADDRES').asString:=trim(DATA.VALUES['ADDRES']);
//        sqlNew.ParamByName('FIO').asString:=trim(DATA.VALUES['FIO']);
//        sqlNew.ParamByName('SUMMA').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['SUMMA']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('INN').asString:=trim(DATA.VALUES['INN']);
//        sqlNew.ParamByName('POSTANOVA').asString:=trim(DATA.VALUES['POSTANOVA']);
//        sqlNew.ParamByName('DATA_POSTANOV').AsDateTime:=StrToDateTimeDef(trim(DATA.VALUES['DATA_POSTANOV']),StrToDateTime('01.01.2001 00:00:01'));//StrToIntDef(DATA.VALUES['DATA_POSTANOV'],0);
//        sqlNew.ParamByName('KOD_ORG').asInteger:=StrToIntDef(trim(DATA.VALUES['KOD_ORG']),0);
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=sqlFreeReturn.FieldByName('ID').asInteger;
//        sqlNew.ParamByName('USLUGA').asInteger:=StrToIntDef(trim(DATA.VALUES['USLUGA']),0);
//        sqlNew.ParamByName('IP').asString:=trim(IPer);
//        sqlNew.ParamByName('DATA').AsDateTime:=Now();//StrToDateTimeDef(trim(DATA.VALUES['DATA']),StrToDateTime('01.01.2001 00:00:01'));
//        sqlNew.ParamByName('KTO_V').asString:=trim(DATA.VALUES['LOGIN']);
//        sqlNew.ParamByName('PER_START').asString:=trim(DATA.VALUES['PER_START']);
//        sqlNew.ParamByName('PER_END').asString:=trim(DATA.VALUES['PER_END']);
//        sqlNew.ParamByName('LS').asInteger:=StrToIntDef(trim(DATA.VALUES['LS']),0);
//        sqlNew.ParamByName('LS_POLUCH').asString:=trim(DATA.VALUES['LS_POLUCH']);
//        sqlNew.ParamByName('KOD_ORG_DOP').asInteger:=StrToIntDef(trim(DATA.VALUES['KOD_ORG_DOP']),0);
//        sqlNew.ParamByName('KOMISIYA').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['KOMISIYA']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('KOMISIYA_BANK').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['KOMISIYA_BANK']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('ORGANIZATION').asString:=trim(DATA.VALUES['ORGANIZATION']);
//        sqlNew.ParamByName('MFO').asString:=trim(DATA.VALUES['MFO']);
//        sqlNew.ParamByName('OKPO').asString:=trim(DATA.VALUES['OKPO']);
//        sqlNew.ParamByName('BANK').asString:=trim(DATA.VALUES['BANK']);
//        sqlNew.ParamByName('R_SHET').asString:=trim(DATA.VALUES['R_SHET']);
//        except
//        Str.Add('except errors params');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        //***********************************
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        //Для возврата текущего присвоенного PAY_ID
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_OPLATA_ID, 0) from rdb$database');
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        GET_USLUGA:=TStringList.Create;
//        GET_USLUGA.ADD('PAY_ID='+sqlNew.FieldBYName('GEN_ID').asString);
//        Result:='200 OK';
//        except
//        sqlNew.Transaction.RollbackRetaining;
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        Str.Add(sqlNew.SQL.Text);
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        Str.SaveToFile('C:\ErrorInsertOplata.txt');
//        exit;
//        end;
//        //***************************************************************
//        END ELSE Result:='500 Error open smen';  //Открытая смена по терминалу
//        Str.Free;
//        END ELSE Result:='500 Error connet to FIB'; //Проверка подключения к базе
//
//        end;
//
////Получение списка отчетов закрепленных на кассе
//        function TDM1.GetReportList(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0)THEN
//        BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT ID, PATH, NAME, ID_TERMINAL FROM REPORT');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('ID_TERMINAL=(SELECT ID FROM TERMINAL WHERE terminal.terminal_id=:ID_TERMINAL)');
//        sqlFree.SelectSQL.Add(' and');
//        sqlFree.SelectSQL.Add(' SEND=1');
//        sqlFree.ParamByName('ID_TERMINAL').AsString:=trim(DATA.VALUES['ID_TERMINAL']);
//        sqlFree.open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=Trim(sqlFree.Fields[i].AsString) else S:=S+'|'+Trim(sqlFree.Fields[i].AsString);
//        end;
//        end;
//        GET_USLUGA.Add(trim(S));
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error ReportList';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
//
////Получение данных счетчиков по PAY_ID
//        function TDM1.GetCounter(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
////   BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT * FROM COUNTER');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('COUNTER.PAY_ID=:PAY_ID');
//        sqlFree.ParamByName('PAY_ID').AsInteger:=StrToInt64Def(trim(DATA.VALUES['PAY_ID']),0);
//        sqlFree.open;
//
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=Trim(sqlFree.Fields[i].AsString) else S:=S+'|'+Trim(sqlFree.Fields[i].AsString);
//        end;
//        end;
//        GET_USLUGA.Add(trim(S));
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error getcounter';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
//
////Получение данных оплаты по LS_POLUCH лицевому счету получателя
//        function TDM1.GetOplataLS(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
////   BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT * FROM oplata');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('OPLATA.LS_POLUCH LIKE :LS_POLUCH and DATA_V>=:DATA and KOD_ORG=:KOD_ORG');
//        sqlFree.ParamByName('LS_POLUCH').AsString:=trim(DATA.VALUES['LS_POLUCH']);
//        sqlFree.ParamByName('DATA').AsDate:=StrToDate(trim(DATA.VALUES['DATA']));
//        sqlFree.ParamByName('KOD_ORG').AsInteger:=StrToInt64Def(trim(DATA.VALUES['KOD_ORG']),6);
//        sqlFree.open;
//
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=Trim(sqlFree.Fields[i].AsString) else S:=S+'|'+Trim(sqlFree.Fields[i].AsString);
//        end;
//        end;
//        GET_USLUGA.Add(trim(S));
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error getoplata';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
////Получение данных оплаты по PAY_ID
//        function TDM1.GetOplata(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
////   BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT * FROM oplata');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('OPLATA.ID=:PAY_ID');
//        sqlFree.ParamByName('PAY_ID').AsInteger:=StrToInt64Def(trim(DATA.VALUES['PAY_ID']),0);
//        sqlFree.open;
//
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=Trim(sqlFree.Fields[i].AsString) else S:=S+'|'+Trim(sqlFree.Fields[i].AsString);
//        end;
//        end;
//        GET_USLUGA.Add(trim(S));
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error getoplata';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
////Получение данных оплаты по KOD_SMEN
//        function TDM1.GetOplataSmena(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
////   BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT * FROM oplata');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('OPLATA.KOD_SMEN=:KOD_SMEN');
//        sqlFree.ParamByName('KOD_SMEN').AsInteger:=StrToInt64Def(trim(DATA.VALUES['KOD_SMEN']),0);
//        sqlFree.open;
//
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=Trim(sqlFree.Fields[i].AsString) else S:=S+'|'+Trim(sqlFree.Fields[i].AsString);
//        end;
//        end;
//        GET_USLUGA.Add(trim(S));
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error getoplata';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
//
////Получение реестра платежей по выбранной смене
//        function TDM1.GetReestr(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        Str:TStringList;
//        i:integer;
//        begin
//        Str:=TStringList.Create;
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT oplata.id, oplata.data, oplata.kod_smen, oplata.fio, oplata.organization, oplata.summa, oplata.storno_id, oplata.komisiya, oplata.ls, oplata.ls_poluch FROM oplata');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('OPLATA.KOD_SMEN=:KOD_SMEN');
//        sqlFree.ParamByName('KOD_SMEN').AsString:=trim(DATA.VALUES['KOD_SMEN']);
//        sqlFree.open;
//        ////////////////////////////////////////////////////////////////////////////////////////
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('UPDATE SMENA SET VERSIONS=:VERSIONS WHERE ID=:KOD_SMEN');
//        sqlNew.ParamByName('VERSIONS').asString:=trim(DATA.Values['VERSIONS']);
//        sqlNew.ParamByName('KOD_SMEN').AsString:=trim(DATA.VALUES['KOD_SMEN']);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        except
//        Str.Add(sqlNew.SQL.Text);
//        Str.Add('VERSIONS:='+sqlNew.ParamByName('VERSIONS').asString);
//        Str.Add('KOD_SMEN:='+sqlNew.ParamByName('KOD_SMEN').asString);
//        Str.SaveToFile('C:\ErrorVersions.txt');
//        end;
//        /////////////////////////////////////////////////////////
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=Trim(sqlFree.Fields[i].AsString) else S:=S+'|'+Trim(sqlFree.Fields[i].AsString);
//        end;
//        end;
//        GET_USLUGA.Add(trim(S));
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error +'+trim(DATA.VALUES['TABLE']);
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        END ELSE Result:='500 Error connect FIB'; //Подключение к базе
//        Str.Free;
//        end;
//
////Получение номеров смены по начиная с указанной даты
//        function TDM1.GetSmena(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
//        //BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('select ID, DATA_N, DATA_K, (select count(id) from oplata');
//        sqlFree.SelectSQL.Add(' where oplata.kod_smen=smena.id) from smena where');
//        sqlFree.SelectSQL.Add('   SMENA.id_terminal=(SELECT ID FROM terminal where TERMINAL_ID=:ID_TERMINAL)');
//        sqlFree.SelectSQL.Add('   and  SMENA.DATA_N>=:DATA_N');
//        sqlFree.SelectSQL.Add('    group by ID, DATA_N, DATA_K');
//        sqlFree.SelectSQL.Add('having (select count(id) from oplata where oplata.kod_smen=smena.id)>0');
//        sqlFree.SelectSQL.Add('order by ID');
//        sqlFree.ParamByName('ID_TERMINAL').AsString:=trim(DATA.VALUES['ID_TERMINAL']);
//        sqlFree.ParamByName('DATA_N').AsDateTime:=StrToDateTimeDef(trim(DATA.Values['DATA_N']),StrToDate('01.01.2012'));
//        sqlFree.open;
//
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=sqlFree.Fields[i].AsString else S:=S+'|'+sqlFree.Fields[i].AsString;
//        end;
//        end;
//        GET_USLUGA.Add(S);
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error GetSmena';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
/////Формирование вывода запроса улицы
//        function TDM1.GetTable(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0)THEN
//        BEGIN
//        //Таблицы BANK, FILIAL, TERMINAL запрашивается из базы терминала
//        if  (trim(DATA.Values['TABLE'])='BANK')or
//        (trim(DATA.Values['TABLE'])='FILIAL')or
//        (trim(DATA.Values['TABLE'])='SOSTAVUSLUG')or
//        (trim(DATA.Values['TABLE'])='TERMINAL')
//        then
//        begin //Подключение к тестовой базе
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Text:='SELECT * FROM '+trim(DATA.VALUES['TABLE']);
//        sqlFree.open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if S='' then S:=sqlFree.Fields[i].AsString else S:=S+'|'+sqlFree.Fields[i].AsString;
//        end;
//        GET_USLUGA.Add(S);
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error +'+trim(DATA.VALUES['TABLE']);
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        end //Конец подключения к тестовой базе
//        else
//        begin//Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        try
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM '+trim(DATA.VALUES['TABLE']);
//        sqlFreeWork.open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFreeWork.FieldCount - 1 do
//        begin
//        if S='' then S:=sqlFreeWork.Fields[i].AsString else S:=S+'|'+sqlFreeWork.Fields[i].AsString;
//        end;
//        GET_USLUGA.Add(S);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error +'+trim(DATA.VALUES['TABLE']);
//        exit;
//        end;
//        END ELSE Result:='500 Error connect work database'; //Подключение к рабочей базе
//        END; //Конец подключения к рабочей базе
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
//        procedure TDM1.OpenSSL1GetPassword(var Password: string);
//        begin
//        password:= 'qazwsx12';
////password:= 'aaaa';
//        end;
//
////Получение списа единых л/с по адресу
//        function TDM1.GetLSFindAdr(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0)THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        //Создадим запрос поиска в базе по адресу
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Clear;
//        sqlFreeWork.SelectSQL.Add('SELECT LS, FIO FROM SHETA WHERE CYTI=:CITY');
//        sqlFreeWork.ParamByName('CITY').asInteger:=StrToIntDef(trim(DATA.VALUES['CITY']),3);
//
//        if trim(DATA.VALUES['STREET'])<>'' then  begin
//        sqlFreeWork.SelectSQL.Add(' and street_nom=:STREET');
//        sqlFreeWork.ParamByName('STREET').asString:=trim(DATA.VALUES['STREET']);
//        end;
//        if trim(DATA.VALUES['HOME'])<>'' then  begin
//        sqlFreeWork.SelectSQL.Add(' and HOME=:HOME');
//        sqlFreeWork.ParamByName('HOME').asString:=trim(DATA.VALUES['HOME']);
//        end;
//        if trim(DATA.VALUES['KORP'])<>'' then  begin
//        sqlFreeWork.SelectSQL.Add(' and KORP=:KORP');
//        sqlFreeWork.ParamByName('KORP').asString:=trim(DATA.VALUES['KORP']);
//        end;
//        if trim(DATA.VALUES['KV'])<>'' then begin
//        sqlFreeWork.SelectSQL.Add(' and KV=:KV');
//        sqlFreeWork.ParamByName('KV').asString:=trim(DATA.VALUES['KV']);
//        end;
//        {      sqlFreeWork.SelectSQL.Text:='SELECT LS, FIO FROM SHETA WHERE CYTI=:CITY and STREET_NOM=:STREET and HOME=:HOME and KORP=:KORP and KV=:KV';
//        sqlFreeWork.ParamByName('KV').asString:=DATA.VALUES['KV'];}
//        try
//
//        sqlFreeWork.Open;
//        GET_USLUGA:=TStringList.Create;
//        except
//        sqlFreeWork.SelectSQL.SaveToFile('C:\ErrorLSFINDADR.txt');
//        Result:='500 Error LSFINDADR';
//        Exit;
//        end;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(sqlFreeWork.fieldByName('LS').asString+'|'+
//        sqlFreeWork.FieldByName('FIO').asString);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        END ELSE BEGIN Result:='500 Error connect work database'; END;
//        END ELSE BEGIN Result:='500 Not open smena';  Exit; END;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
////Поиск по адрему
//        function TDM1.GetFindAdr(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0)THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        //Создадим запрос поиска в базе по адресу
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM SHETA WHERE CYTI=:CITY and STREET_NOM=:STREET and HOME=:HOME and KORP=:KORP and KV=:KV';
//        sqlFreeWork.ParamByName('CITY').asInteger:=StrToIntDef(trim(DATA.VALUES['CITY']),3);
//        sqlFreeWork.ParamByName('STREET').asInteger:=StrToIntDef(trim(DATA.VALUES['STREET']),0);
//        sqlFreeWork.ParamByName('HOME').asString:=trim(DATA.VALUES['HOME']);
//        sqlFreeWork.ParamByName('KORP').asString:=trim(DATA.VALUES['KORP']);
//        sqlFreeWork.ParamByName('KV').asString:=trim(DATA.VALUES['KV']);
//        try
//        sqlFreeWork.Open;
//        except
//        Result:='500 Error FINDADR';
//        Exit;
//        end;
//        LS:=sqlFreeWork.FieldByName('LS').asInteger;
//        if LS>0 then
//        BEGIN
//
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS)';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        //sqlFreeWork.ParamByName('KOD_ORG').asString:=DATA.VALUES['KOD_ORG'];
//        try
//        sqlFreeWork.open;
//        except
//        Result:='500 Error FINDADR (GET_USLUGA)';
//        exit;
//        end;
//
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(sqlFreeWork.fieldByName('DTM').asString+'|'+
//        sqlFreeWork.FieldByName('USLUGANAME').asString+'|'+
//        sqlFreeWork.FieldByName('IDEN_SHET').asString+'|'+
//        sqlFreeWork.FieldByName('USLUGA').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_PRED').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_TEK').asString+'|'+
//        sqlFreeWork.FieldByName('FIO').asString+'|'+
//        sqlFreeWork.FieldByName('TARIF').asString+'|'+
//        sqlFreeWork.FieldByName('KOPLATE').asString+'|'+
//        sqlFreeWork.FieldByName('ADDRESS').asString+'|'+
//        sqlFreeWork.FieldByName('NS').asString+'|'+
//        sqlFreeWork.FieldByName('LS_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('KOD_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('ORGANIZATION').asString+'|'+
//        sqlFreeWork.FieldByName('MFO').asString+'|'+
//        sqlFreeWork.FieldByName('OKPO').asString+'|'+
//        sqlFreeWork.FieldByName('BANK').asString+'|'+
//        sqlFreeWork.FieldByName('R_SHET').asString);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        end;
//        END ELSE BEGIN Result:='500 Error connect work database'; END;
//        END ELSE BEGIN Result:='500 Not open smena';  Exit; END;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
////Получение всех возможных данных по л/с поставщика и единому л/с
//        function TDM1.GetDataFull(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(Data)>0)THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        //Создадим запрос поиска к базе, сначала получим единый счет по подключению
//        sqlFreeWork1.Close;
//        sqlFreeWork1.SelectSQL.Text:='select distinct ls from LS_SHET where NAME =:LS_DBF or LS =:LS_DBF';
//        sqlFreeWork1.ParamByName('LS_DBF').asString:=trim(DATA.VALUES['LS']);
//        try
//        sqlFreeWork1.Open;
//        except
//        Result:='500 Error select LS';
//        Exit;
//        end;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork1.EOF do
//        begin
//        try
//        LS:=sqlFreeWork1.FieldByName('LS').asInteger;
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS)';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        sqlFreeWork.open;
//        except
//        Result:='500 Error GET_USLUGA';
//        exit;
//        end;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFreeWork.fieldByName('DTM').AsDateTime)+'|'+
//        sqlFreeWork.FieldByName('USLUGANAME').asString+'|'+
//        sqlFreeWork.FieldByName('IDEN_SHET').asString+'|'+
//        sqlFreeWork.FieldByName('USLUGA').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_PRED').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_TEK').asString+'|'+
//        sqlFreeWork.FieldByName('FIO').asString+'|'+
//        sqlFreeWork.FieldByName('TARIF').asString+'|'+
//        sqlFreeWork.FieldByName('KOPLATE').asString+'|'+
//        sqlFreeWork.FieldByName('ADDRESS').asString+'|'+
//        sqlFreeWork.FieldByName('NS').asString+'|'+
//        sqlFreeWork.FieldByName('LS_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('KOD_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('ORGANIZATION').asString+'|'+
//        sqlFreeWork.FieldByName('MFO').asString+'|'+
//        sqlFreeWork.FieldByName('OKPO').asString+'|'+
//        sqlFreeWork.FieldByName('BANK').asString+'|'+
//        sqlFreeWork.FieldByName('R_SHET').asString);
//        sqlFreeWork.Next;
//        end;
//        sqlFreeWork1.Next;
//        END; //FreeWork1
//        Result:='200 OK';
//        Exit;
//        END ELSE BEGIN Result:='500 Error connect work database'; Exit; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect to FIB';
//        end;
//
////Получение данных по л/с поставщика услуг
//        function TDM1.GetData(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(Data)>0)THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        //Создадим запрос поиска к базе, сначала получим единый счет по подключению
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT LS, NAME, KOD_ORG FROM LS_SHET WHERE NAME =:LS_DBF and KOD_ORG=:KOD_ORG';
//        sqlFreeWork.ParamByName('LS_DBF').asString:=trim(DATA.VALUES['LS']);
//        sqlFreeWork.ParamByName('KOD_ORG').asString:=trim(DATA.VALUES['KOD_ORG']);
//        try
//        sqlFreeWork.Open;
//        except
//        Result:='500 Error select LS';
//        Exit;
//        end;
//
//        try
//        LS:=sqlFreeWork.FieldByName('LS').asInteger;
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS) WHERE KOD_POLUCH=:KOD_ORG';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        sqlFreeWork.ParamByName('KOD_ORG').asString:=trim(DATA.VALUES['KOD_ORG']);
//        sqlFreeWork.open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFreeWork.fieldByName('DTM').AsDateTime)+'|'+
//        sqlFreeWork.FieldByName('USLUGANAME').asString+'|'+
//        sqlFreeWork.FieldByName('IDEN_SHET').asString+'|'+
//        sqlFreeWork.FieldByName('USLUGA').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_PRED').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_TEK').asString+'|'+
//        sqlFreeWork.FieldByName('FIO').asString+'|'+
//        sqlFreeWork.FieldByName('TARIF').asString+'|'+
//        sqlFreeWork.FieldByName('KOPLATE').asString+'|'+
//        sqlFreeWork.FieldByName('ADDRESS').asString+'|'+
//        sqlFreeWork.FieldByName('NS').asString+'|'+
//        sqlFreeWork.FieldByName('LS_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('KOD_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('ORGANIZATION').asString+'|'+
//        sqlFreeWork.FieldByName('MFO').asString+'|'+
//        sqlFreeWork.FieldByName('OKPO').asString+'|'+
//        sqlFreeWork.FieldByName('BANK').asString+'|'+
//        sqlFreeWork.FieldByName('R_SHET').asString);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error GET_USLUGA';
//        exit;
//        end;
//        END ELSE BEGIN Result:='500 Error connect work database'; Exit; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect to FIB';
//        end;
//
////Поиск по единому лицевому счету
//        function TDM1.GetFindLS(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0)THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        IF ConnectFIBWORK(USER,PASSWD,DB_WORK) THEN
//        BEGIN
//        LS:=StrToIntDef(trim(DATA.VALUES['LS']),0);
//        if LS>0 then
//        BEGIN
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS)';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        try
//        sqlFreeWork.open;
//        Result:='200 OK';
//        except
//        Result:='500 Error FINDLS (GET_USLUGA)';
//        exit;
//        end;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFreeWork.fieldByName('DTM').AsDateTime)+'|'+
//        sqlFreeWork.FieldByName('USLUGANAME').asString+'|'+
//        sqlFreeWork.FieldByName('IDEN_SHET').asString+'|'+
//        sqlFreeWork.FieldByName('USLUGA').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_PRED').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_TEK').asString+'|'+
//        sqlFreeWork.FieldByName('FIO').asString+'|'+
//        sqlFreeWork.FieldByName('TARIF').asString+'|'+
//        sqlFreeWork.FieldByName('KOPLATE').asString+'|'+
//        sqlFreeWork.FieldByName('ADDRESS').asString+'|'+
//        sqlFreeWork.FieldByName('NS').asString+'|'+
//        sqlFreeWork.FieldByName('LS_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('KOD_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('ORGANIZATION').asString+'|'+
//        sqlFreeWork.FieldByName('MFO').asString+'|'+
//        sqlFreeWork.FieldByName('OKPO').asString+'|'+
//        sqlFreeWork.FieldByName('BANK').asString+'|'+
//        sqlFreeWork.FieldByName('R_SHET').asString);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        end;
//        END ELSE BEGIN Result:='500 Error connect FIBWORK'; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 error connect FIB';
//        end;
//
////Оформление сторно платежа
//
//        function TDM1.SetStorno(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        ID_TERM, i:integer;
//        STORNO:Int64;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //Если сессия открыта для данного терминала
//        ID_TERM:=TerminalID(DATA);
//        if (ID_TERM>0) THEN
//        BEGIN
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO STORNO (DATA, SUMMA, PAY_ID, TIP)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:DATA, :SUMMA, :PAY_ID, :TIP)');
//        sqlNew.ParamByName('DATA').AsDateTime:=StrToDateTime(trim(DATA.VALUES['DATA']));
//        sqlNew.ParamByName('SUMMA').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['SUMMA']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('PAY_ID').asInteger:=StrToInt64Def(trim(DATA.Values['PAY_ID']),0);
//        sqlNew.ParamByName('TIP').asInteger:=StrToIntDef(trim(DATA.Values['TIP']),0);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        //Узнаем текущее значение генератора сторно
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_STORNO_ID, 0) from rdb$database');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        STORNO:=sqlNew.FieldByName('GEN_ID').AsInt64;
//        except
//        Result:='500 Error get generator storno';//+Data.Text;
//        Exit;
//        end;
//        //Теперь обновим запись платежа
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('UPDATE OPLATA SET STORNO_ID=:STORNO WHERE ID=:PAY_ID');
//        sqlNew.ParamByName('STORNO').AsInt64:=STORNO;
//        sqlNew.ParamByName('PAY_ID').AsInt64:=StrToInt64Def(trim(DATA.Values['PAY_ID']),0);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        except
//        Result:='500 Error update oplata';//+Data.Text;
//        Exit;
//        end;
//
//
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
////Получение идентификатора почки банкнот из Кешкодера
//        function TDM1.SetCurrence(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        ID_TERM,SMENA:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //Если сессия открыта для данного терминала
//        ID_TERM:=TerminalID(DATA);
//        if (ID_TERM>0) THEN
//        BEGIN
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select id from smena where  id_terminal=:ID_TERM');
//        sqlNew.SQL.Add('and data_n=(select max(data_n) from smena where id_terminal=:ID_TERM)');
//        sqlNew.ParamByName('ID_TERM').asInteger:=ID_TERM;
//        sqlNew.ExecQuery;
//        SMENA:=sqlNew.FieldByName('ID').AsInteger;
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO CURRENCY (NOMINAL, ID_CURRENCE, SMENA)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:NOMINAL, :ID_CURRENCE, :SMENA)');
//        sqlNew.ParamByName('NOMINAL').asInteger:=StrToIntDef(trim(DATA.VALUES['NOM']),0);
//        sqlNew.ParamByName('ID_CURRENCE').asInteger:=StrToIntDef(trim(DATA.VALUES['ID_CURRENCE']),0);
//        sqlNew.ParamByName('SMENA').asInteger:=SMENA;
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//////////////Начало оплаты генерирование номера ID_CURRENCE
//        function TDM1.StartOplata(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        //Определим значение генератора пачек денег кешкодера
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        GET_USLUGA:=TStringList.Create;
//        GET_USLUGA.Add('ID_CURRENCE='+sqlNew.fieldByName('GEN_ID').asString);
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Str.Add('Error exec select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        Result:='500 Error SET_GEN';
//        Exit;
//        end;
//
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
////Закрытие процедуры оплаты
////Процедуру будет отключена как устаревшая
//        function TDM1.CloseOplata(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //Если сессия открыта для данного терминала
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        {//Определим значение генератора пачек денег кешкодера
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining; }
//        Result:='200 OK';
//        {except
//        Str.Add('Error exec select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        Result:='500 Error SET_GEN';
//        Exit;
//        end; }
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
////Возвращает адрес терминала
//        function TDM1.GetAddrTerm(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        GET_USLUGA:=TStringList.Create;
//        {sqlNew.Close;
//        sqlNew.SQL.Text:='SELECT ADDRES FROM TERMINAL WHERE ID='+intToStr(TerminalID(DATA));
//        sqlNew.ExecQuery;}
//        GET_USLUGA.Add('ADDRES='+sqlFreeReturn.fieldByName('ADDRES').asString);
//        Result:='200 OK';
//        Exit;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE result:='500 Error connect FIB';
//        end;
//
////Возвращает список расчета комиссии
//        function TDM1.GetKomisiya(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        sqlNew.SQL.Add('SELECT * from CALCULATE_KOMISSIA(');
//        sqlNew.SQL.add(':USLUGA_ID,');
//        sqlNew.SQL.add(':ORGANIZATION_ID,');
//        if StrToIntDef(trim(DATA.Values['BANK_ID']),0)>0 then sqlNew.SQL.add(':BANK_ID,') else sqlNew.SQL.add('null,');
//        if StrToIntDef(trim(DATA.Values['FILIAL_ID']),0)>0 then sqlNew.SQL.add(':FILIAL_ID,') else sqlNew.SQL.add('null, ');
//        sqlNew.SQL.add(':ID_TERMINAL,');
//        if StrToIntDef(trim(DATA.Values['KOMISSIA_TYPE_ID']),0)>0 then sqlNew.SQL.add(':KOMISSIA_TYPE_ID,') else sqlNew.SQL.add('null,');
//        sqlNew.SQL.add(':SUMMA_PAY)');
//
//        //sqlNew.SQL.SaveToFile('C:\123.txt');
//
//        sqlFree.Close;
//        sqlFree.SelectSQL.Text:='SELECT * FROM TERMINAL WHERE TERMINAL_ID=:TERMINAL_ID';
//        sqlFree.ParamByName('TERMINAL_ID').AsString:=trim(DATA.Values['ID_TERMINAL']);
//        sqlFree.Open;
//
//
//
//
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=sqlFree.FieldByName('ID').AsInteger;//StrToIntDef(trim(DATA.Values['ID_TERMINAL']),1);
//        sqlNew.ParamByName('USLUGA_ID').asInteger:=StrToIntDef(trim(DATA.Values['USLUGA']),1);
//        sqlNew.ParamByName('ORGANIZATION_ID').asInteger:=StrToIntDef(trim(DATA.Values['KOD_ORG']),1);
//
//        if trim(DATA.Values['BANK_ID'])>'0' then sqlNew.ParamByName('BANK_ID').asInteger:=StrToIntDef(trim(DATA.Values['BANK_ID']),0);
//        if trim(DATA.Values['FILIAL_ID'])>'0' then sqlNew.ParamByName('FILIAL_ID').asInteger:=StrToIntDef(trim(DATA.Values['FILIAL_ID']),0);
//        if trim(DATA.Values['KOMISSIA_TYPE_ID'])>'0' then sqlNew.ParamByName('KOMISSIA_TYPE_ID').asInteger:=StrToIntDef(trim(DATA.Values['KOMISSIA_TYPE_ID']),0);
//
//
//        sqlNew.ParamByName('SUMMA_PAY').AsFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['SUMMA']),'.',DecimalSeparator),0);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlNew.EOF do
//        begin
//        GET_USLUGA.Add(Trim('OUR_SUM_INNER='+sqlNew.FieldByName('OUR_SUM_INNER').asString));
//        GET_USLUGA.Add(Trim('OUT_SUM_INNER='+sqlNew.FieldByName('OUT_SUM_INNER').asString));
//        GET_USLUGA.Add(Trim('OUR_SUM_OUTER='+sqlNew.FieldByName('OUR_SUM_OUTER').asString));
//        GET_USLUGA.Add(Trim('OUT_SUM_OUTER='+sqlNew.FieldByName('OUT_SUM_OUTER').asString));
//        sqlNew.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        sqlNew.SQL.SaveToFile('C:\ErrorKomissiya.txt');
//        Result:='500 Error KOMISIYA';
//        exit;
//        end;
//
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
////Возвращает список состава услуг
//        function TDM1.GetSostav(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        GET_USLUGA:=TStringList.Create;
//        sqlNew.Close;
//        sqlNew.SQL.Text:='SELECT ID, USLUGA, NAME, TIP_RASHETA, ZNACH, KOD_ORG, DOP_USLUGA, DOP_ORGANIZATION, R_SHET, NAZNACHENIE, OKPO FROM SOSTAVUSLUG WHERE USLUGA='+trim(DATA.Values['USLUGA'])+' and KOD_ORG='+trim(DATA.Values['KOD_ORG']);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        while not sqlNew.EOF do
//        begin
//        GET_USLUGA.Append(sqlNew.fieldByName('USLUGA').asString+'|'+
//        sqlNew.fieldByName('NAME').asString+'|'+
//        sqlNew.FieldByName('TIP_RASHETA').asString+'|'+
//        sqlNew.FieldByName('ZNACH').asString+'|'+
//        sqlNew.FieldByName('KOD_ORG').asString+'|'+
//        sqlNew.FieldByName('DOP_USLUGA').asString+'|'+
//        sqlNew.FieldByName('DOP_ORGANIZATION').asString+'|'+
//        sqlNew.FieldByName('R_SHET').asString+'|'+
//        sqlNew.FieldByName('NAZNACHENIE').asString+'|'+
//        sqlNew.FieldByName('OKPO').asString
//        );
//        sqlNew.Next;
//        end;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error SOSTAVUSLUG';
//        exit;
//        end;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//
////Возвращает список уникальных значений домов, корпусов или квартир
//        function TDM1.GetAddres(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        IF ConnectFIBWORK(USER,PASSWD,DB_WORK) THEN
//        BEGIN
//        if trim(DATA.VALUES['PARAMS'])='HOME' then
//        BEGIN
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT DISTINCT HOME, KORP, CYTI FROM SHETA';
//        try
//        sqlFreeWork.open;
//        Result:='200 OK';
//        except
//        Result:='500 Error GETADDRES';
//        exit;
//        end;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(trim(sqlFreeWork.fieldByName('HOME').asString)+'|'+trim(sqlFreeWork.FieldByName('KORP').asString)+'|'+trim(sqlFreeWork.FieldByName('CYTI').asString));
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        END ELSE if trim(DATA.VALUES['PARAMS'])='KV' then
//        BEGIN
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT DISTINCT(KV) FROM SHETA';
//        try
//        sqlFreeWork.open;
//        Result:='200 OK';
//        except
//        Result:='500 Error GETADDRES';
//        exit;
//        end;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(trim(sqlFreeWork.fieldByName('KV').asString)+'|');
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        END;
//        END ELSE BEGIN Result:='500 Error connect FIBWORK'; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//
//
//
//
//
//
//