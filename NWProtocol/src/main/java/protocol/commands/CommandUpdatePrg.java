package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда updateprg
//        Выполняет запрос на получение файла программы
//        updateprg при успешном выполнении возвращает UPDATEPRG;
//        1.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        FILENAME = Имя файла программного обеспечения, обязательный параметр;
//        DIR =  Директория сохранения полученного файла, обязательный параметр;
//        PATHPRG = Директория место расположения файла на сервере, если параметр не указан, тогда устанавливается директория размещения файлов на сервере определенная в протоколе по умолчанию
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        3.	Далее сервер возвращает потоком TStream полученный файл с сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandUpdatePrg extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandUpdatePrg.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandUpdatePrg tryParseCommand(String commandData) {

        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}

////*/////////////////////////////////////////////////////////////////////////////////////
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
