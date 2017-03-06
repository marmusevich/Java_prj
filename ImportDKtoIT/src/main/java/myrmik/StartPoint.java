package myrmik;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.*;


/**
 * Created by asus on 22.02.2017.
 */
public class StartPoint {
    private static final String FILE_NAME = "DK_021_2015.txt";

    private static final String DB_NAME = "iot";

    private static final String GRUP = "W210000000";

    public static void main(String[] args) {
//        SQLDatabaseConnection();

        StartPoint sp = new StartPoint();
        sp.main();
    }


    // Connect to your database.
    public static void SQLDatabaseConnection() {
        //CFG_SQL     = DRIVER={SQL Server}; SERVER={192.168.10.8}; UID={sa}; PWD={291263}; DATABASE={IOT};
        // Network={DBMSSOCN}; APP={IT.%DATABASE%}; WSID={%WSID%}

        String connectionString =
                "jdbc:sqlserver://192.168.10.8:1433;"
                        + "database=" + DB_NAME + ";"
                        + "user=sa;"
                        + "password=291263;"
                        + "loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;


        // insert
        //PreparedStatement prepsInsertProduct = null;

        try {
            // select
            {
                connection = DriverManager.getConnection(connectionString);

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT TOP 10 KMAT,NMAT FROM KSM where SKM = 'W100000000'";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSql);

                // Print results from select statement
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
                }

            }


            // insert
//            {
//                connection = DriverManager.getConnection(connectionString);
//                // Create and execute an INSERT SQL prepared statement.
//                String insertSql = "INSERT INTO SalesLT.Product (Name, ProductNumber, Color, StandardCost, ListPrice, SellStartDate) VALUES "
//                        + "('NewBike', 'BikeNew', 'Blue', 50, 120, '2016-01-01');";
//
//                prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
//                prepsInsertProduct.execute();
//
//                // Retrieve the generated key from the insert.
//                resultSet = prepsInsertProduct.getGeneratedKeys();
//
//                // Print the ID of the inserted row.
//                while (resultSet.next()) {
//                    System.out.println("Generated: " + resultSet.getString(1));
//                }
//
//            }




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connections after the data has been handled.
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }


    public void main() {
        String connectionString =
                "jdbc:sqlserver://192.168.10.8:1433;"
                        + "database=" + DB_NAME + ";"
                        + "user=sa;"
                        + "password=291263;"
                        + "loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString);

            readFile(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    //прочитать файл и записать в бд
    public void readFile(Connection con) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(FILE_NAME), Charset.forName("windows-1251")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line != null && line.length() > 0) {

                    String[] words = line.split("\t");
                    if (words != null && words.length == 2) {
                        String code = words[0].trim();
                        String caption_ = words[1].trim();
                        String caption = caption_.replace("'", "’");

                        //---
                        updateDB(con, count, code, caption);

                        count++;
                    } else {
                        System.out.println("line is not world  ->" + line);
                    }
                } else {
                    System.out.println("line is NULL ->" + line);
                }
            }
            System.out.println("count = " + count);
        } catch (IOException e) {
            // log error
            count = -1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public void updateDB(Connection con, int numer, String code, String caption) throws SQLException {
        //private static final String GRUP = "W210000000";
        //SKM = 'W100000000'
        //      'W10000000008991'
        String KMAT = String.format("%s%05d", GRUP, numer);
        String N_RES = String.format("%s %s", code, caption);

        System.out.println(String.format("%05d - (%s) -> %s", numer, code, caption));



        Statement statement = null;
        ResultSet resultSet = null;
        // проверить есть ли такой код

        // нет  - добавить
        PreparedStatement prepsInsertProduct = null;
        String insertSql = "INSERT INTO KSM "
                + " (KMAT, NMAT, NAIMKM_S, N_RES, N_RES_DOC, SKM, EDI, KBLS, BS, FIO_D, DATE_D,  "
                + "EDI_NORM, EDI_NORMP, KPER_NORM, KPER_N_I, EDI2, KPER2, KPER2_I, EDI3, KPER3, KPER3_I, EDI_NKALK )"
                + " VALUES ( "
                + "'" + KMAT + "', '" + caption + "', '" + code + "', '" + N_RES + "', '" + N_RES + "', '" + GRUP + "', 999, 'УКР', '364/6', 'auto', '2017-03-03', "
                + "999, 999, 1.000000, 1.000000, 999, 1.000000, 1.000000, 999, 1.000000, 1.000000, 999 )";
        prepsInsertProduct = con.prepareStatement(insertSql);
        prepsInsertProduct.execute();

        // да обновить






//        String insertSql = "INSERT INTO KSM "
//                + "(KMAT,NMAT,NAIMKM_S,N_RES,N_RES_DOC,PRNAIM,SKM,KKST,EDI,EDI_NORM,EDI_NORMP,KPER_NORM,KPER_N_I "
//                + ",EDI2,KPER2,KPER2_I,EDI3,KPER3,KPER3_I,EDI_NKALK,BCH_SIZE,KMAT_MAT1,NMAT_MAT1,KMAT_MAT2,N1_MAT "
//                + ",RESGZ,MODELST,KODZAG,KP001,NUMIOT,GOST,ARTIKUL,RN_SERT,UN_SERT,D_SERT,KSKL,KBLS,BS,KAU "
//                + ",PR_OTH,KMAT_OTH,CENA_PL,CENA_PLU,DATECENPL,PR_TPR,CEH_N,PCIK,KOLMP,NZAPK,NZAPD,PR_CESZ,KCESZ "
//                + ",OLDKMAT,OLDKMAT1,OLDKMAT2,KMATGP_O,KMATGP_O1,KMAT_KALK,KKS1,KKS2,KKS3,KSBG,KF1,KF2,KF3,KF4,PR_DM "
//                + ",COMM,KOKP,KTNVED,KMATGP_PMZ,HKMAT,ORG_IZG,KSP1,KTKP,KFRM,PRGRSPEC,PR_NORMAL,BARCODE,NMAT_PR,KDSN "
//                + ",PR_DO,BS_Z,KAU_Z,ABC,PLZAKUP,FIO_R,DATE_R,PR_CH_NDOC,KTKP_VP,KMAT_GOST1,KMAT_GOST2,KMAT_GOST3,PRC_TOLR "
//                + ",KTTII,UNTTII,FIO_O,DATE_K,STDCURR,FIO_D,DATE_D,HEIGHT,LENGHT,WIDTH,MASS,DENSITY,PRIORITET,KMPS1 "
//                + ",NZAPPK,PCIKP,PCIKM,KEEP_TIME,KOLMPR,PLPRV,PLPRVDATA,KFPU,VZAK,RZAP1D) "  //,UNKSM,KGKD
//                + "VALUES ("
//                + "'" + KMAT + "', '" + caption + "', '" + code + "', '" + N_RES + "', '" + N_RES + "', 0, '" + GRUP + "', "
//                + "'Б', 999, 999, 999, 1.000000, 1.000000, 999, 1.000000, 1.000000, 999, 1.000000, 1.000000, 999, '', '',"
//                + "NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', NULL, NULL, " +
//                + "'УКР', '364/6', NULL, 0, NULL, 0.00, 0.00, NULL, ' ', NULL, 0, 0.000000, 0.000000, 0.0, '', NULL, "
//                + "'', '', '', NULL, NULL, NULL, '', ,'', '', '', 0.0000, 0.0000, 0.0000, 0.0000, '', '', '', '', NULL, NULL, 0, 0, NULL, "
//                + "'', 0, '', '', '', 0, '', NULL, NULL,  '', '', '', NULL, 1, NULL,  '', '', '', 0.0, '', 0, '', NULL, '', "
//                + "'ФУРМАН', '2017-03-03, 0.000000, 0.000000, 0.000000, 0.000000, 0.000000, 0, '', "
//                + "0.000000, 0, 0, 0, 0.000000, '', 0, NULL, 0.000000, 0.000000, "
//                + ")";
    }
}




//    /****** Сценарий для команды SelectTopNRows среды SSMS  ******/
//    SELECT TOP 1000 KMAT,NMAT,NAIMKM_S,N_RES,N_RES_DOC,PRNAIM,SKM,KKST,EDI,EDI_NORM,EDI_NORMP,KPER_NORM
//        ,KPER_N_I,EDI2,KPER2,KPER2_I,EDI3,KPER3,KPER3_I,EDI_NKALK,BCH_SIZE,KMAT_MAT1,NMAT_MAT1,KMAT_MAT2
//        ,N1_MAT,RESGZ,MODELST,KODZAG,KP001,NUMIOT,GOST,ARTIKUL,RN_SERT,UN_SERT,D_SERT,KSKL,KBLS
//        ,BS,KAU,PR_OTH,KMAT_OTH,CENA_PL,CENA_PLU,DATECENPL,PR_TPR,CEH_N,PCIK,KOLMP,NZAPK,NZAPD
//        ,PR_CESZ,KCESZ,OLDKMAT,OLDKMAT1,OLDKMAT2,KMATGP_O,KMATGP_O1,KMAT_KALK,KKS1,KKS2,KKS3
//        ,KSBG,KF1,KF2,KF3,KF4,PR_DM,COMM,KOKP,KTNVED,KMATGP_PMZ,HKMAT,ORG_IZG,KSP1,KTKP
//        ,KFRM,PRGRSPEC,PR_NORMAL,BARCODE,NMAT_PR,KDSN,PR_DO,BS_Z,KAU_Z,ABC,PLZAKUP,FIO_R,DATE_R
//        ,PR_CH_NDOC,KTKP_VP,KMAT_GOST1,KMAT_GOST2,KMAT_GOST3,PRC_TOLR,KTTII,UNTTII,FIO_O,DATE_K,STDCURR
//        ,FIO_D,DATE_D,HEIGHT,LENGHT,WIDTH,MASS,DENSITY,PRIORITET,KMPS1,NZAPPK,PCIKP,PCIKM,KEEP_TIME
//        ,KOLMPR,PLPRV,PLPRVDATA,KFPU,VZAK,RZAP1D,UNKSM,KGKD
//        FROM KSM





//    INSERT INTO KSM
//        (KMAT,NMAT,NAIMKM_S,N_RES,N_RES_DOC,PRNAIM,SKM,KKST,EDI,EDI_NORM,EDI_NORMP,KPER_NORM,KPER_N_I
//        ,EDI2,KPER2,KPER2_I,EDI3,KPER3,KPER3_I,EDI_NKALK,BCH_SIZE,KMAT_MAT1,NMAT_MAT1,KMAT_MAT2,N1_MAT
//        ,RESGZ,MODELST,KODZAG,KP001,NUMIOT,GOST,ARTIKUL,RN_SERT,UN_SERT,D_SERT,KSKL,KBLS,BS,KAU
//        ,PR_OTH,KMAT_OTH,CENA_PL,CENA_PLU,DATECENPL,PR_TPR,CEH_N,PCIK,KOLMP,NZAPK,NZAPD,PR_CESZ,KCESZ
//        ,OLDKMAT,OLDKMAT1,OLDKMAT2,KMATGP_O,KMATGP_O1,KMAT_KALK,KKS1,KKS2,KKS3,KSBG,KF1,KF2,KF3,KF4,PR_DM
//        ,COMM,KOKP,KTNVED,KMATGP_PMZ,HKMAT,ORG_IZG,KSP1,KTKP,KFRM,PRGRSPEC,PR_NORMAL,BARCODE,NMAT_PR,KDSN
//        ,PR_DO,BS_Z,KAU_Z,ABC,PLZAKUP,FIO_R,DATE_R,PR_CH_NDOC,KTKP_VP,KMAT_GOST1,KMAT_GOST2,KMAT_GOST3,PRC_TOLR
//        ,KTTII,UNTTII,FIO_O,DATE_K,STDCURR,FIO_D,DATE_D,HEIGHT,LENGHT,WIDTH,MASS,DENSITY,PRIORITET,KMPS1
//        ,NZAPPK,PCIKP,PCIKM,KEEP_TIME,KOLMPR,PLPRV,PLPRVDATA,KFPU,VZAK,RZAP1D,UNKSM,KGKD)
//        VALUES
//        (<KMAT, char(15),>,<NMAT, varchar(200),>,<NAIMKM_S, varchar(30),>,<N_RES, varchar(254),>,<N_RES_DOC, varchar(254),>,<PRNAIM, smallint,>
//           ,<SKM, char(15),>,<KKST, char(1),>,<EDI, smallint,>,<EDI_NORM, smallint,>,<EDI_NORMP, smallint,>,<KPER_NORM, decimal(14,6),>,<KPER_N_I, decimal(14,6),>
//        ,<EDI2, smallint,>,<KPER2, decimal(14,6),>,<KPER2_I, decimal(14,6),>,<EDI3, smallint,>,<KPER3, decimal(14,6),>,<KPER3_I, decimal(14,6),>
//        ,<EDI_NKALK, smallint,>,<BCH_SIZE, varchar(40),>,<KMAT_MAT1, char(15),>,<NMAT_MAT1, varchar,>,<KMAT_MAT2, char(15),>,<N1_MAT, char(1),>
//        ,<RESGZ, varchar(40),>,<MODELST, varchar(25),>,<KODZAG, decimal(10,0),>,<KP001, char(10),>,<NUMIOT, char(10),>,<GOST, varchar(50),>
//        ,<ARTIKUL, varchar(30),>,<RN_SERT, char(20),>,<UN_SERT, char(10),>,<D_SERT, datetime,>,<KSKL, int,>,<KBLS, char(5),>,<BS, char(10),>
//        ,<KAU, varchar(12),>,<PR_OTH, smallint,>,<KMAT_OTH, char(15),>,<CENA_PL, decimal(12,2),>,<CENA_PLU, decimal(12,2),>,<DATECENPL, datetime,>
//           ,<PR_TPR, char(1),>,<CEH_N, int,>,<PCIK, smallint,>,<KOLMP, decimal(13,6),>,<NZAPK, decimal(13,6),>,<NZAPD, decimal(5,1),>,<PR_CESZ, char(1),>
//        ,<KCESZ, char(2),>,<OLDKMAT, varchar(30),>,<OLDKMAT1, varchar(30),>,<OLDKMAT2, varchar(254),>,<KMATGP_O, char(15),>,<KMATGP_O1, char(15),>
//        ,<KMAT_KALK, char(15),>,<KKS1, char(5),>,<KKS2, char(3),>,<KKS3, char(4),>,<KSBG, char(3),>,<KF1, decimal(11,4),>,<KF2, decimal(11,4),>,<KF3, decimal(11,4),>
//        ,<KF4, decimal(11,4),>,<PR_DM, char(1),>,<COMM, varchar(35),>,<KOKP, varchar(15),>,<KTNVED, varchar(12),>,<KMATGP_PMZ, char(15),>,<HKMAT, varchar,>
//           ,<ORG_IZG, int,>,<KSP1, smallint,>,<KTKP, char(2),>,<KFRM, char(2),>,<PRGRSPEC, smallint,>,<PR_NORMAL, char(1),>,<BARCODE, varchar(13),>
//        ,<NMAT_PR, varchar(30),>,<KDSN, smallint,>,<PR_DO, char(1),>,<BS_Z, char(10),>,<KAU_Z, varchar(12),>,<ABC, char(1),>,<PLZAKUP, char(1),>,<FIO_R, varchar(10),>
//        ,<DATE_R, datetime,>,<PR_CH_NDOC, char(1),>,<KTKP_VP, char(2),>,<KMAT_GOST1, char(15),>,<KMAT_GOST2, char(15),>,<KMAT_GOST3, char(15),>,<PRC_TOLR, decimal(4,1),>
//        ,<KTTII, varchar(30),>,<UNTTII, int,>,<FIO_O, varchar(10),>,<DATE_K, datetime,>,<STDCURR, char(1),>,<FIO_D, varchar(10),>,<DATE_D, datetime,>,<HEIGHT, decimal(13,6),>
//        ,<LENGHT, decimal(13,6),>,<WIDTH, decimal(13,6),>,<MASS, decimal(13,6),>,<DENSITY, decimal(13,6),>,<PRIORITET, smallint,>,<KMPS1, char(1),>,<NZAPPK, decimal(13,6),>
//        ,<PCIKP, smallint,>,<PCIKM, smallint,>,<KEEP_TIME, smallint,>,<KOLMPR, decimal(13,6),>,<PLPRV, char(1),>,<PLPRVDATA, smallint,>,<KFPU, varchar(3),>,<VZAK, decimal(13,6),>
//        ,<RZAP1D, decimal(13,6),>,<UNKSM, decimal(10,0),>,<KGKD, char(6),>)
//        KMAT	NMAT	NAIMKM_S	N_RES	N_RES_DOC	PRNAIM	SKM	KKST	EDI	EDI_NORM	EDI_NORMP	KPER_NORM	KPER_N_I	EDI2	KPER2	KPER2_I	EDI3	KPER3	KPER3_I	EDI_NKALK	BCH_SIZE	KMAT_MAT1	NMAT_MAT1	KMAT_MAT2	N1_MAT	RESGZ	MODELST	KODZAG	KP001	NUMIOT	GOST	ARTIKUL	RN_SERT	UN_SERT	D_SERT	KSKL	KBLS	BS	KAU	PR_OTH	KMAT_OTH	CENA_PL	CENA_PLU	DATECENPL	PR_TPR	CEH_N	PCIK	KOLMP	NZAPK	NZAPD	PR_CESZ	KCESZ	OLDKMAT	OLDKMAT1	OLDKMAT2	KMATGP_O	KMATGP_O1	KMAT_KALK	KKS1	KKS2	KKS3	KSBG	KF1	KF2	KF3	KF4	PR_DM	COMM	KOKP	KTNVED	KMATGP_PMZ	HKMAT	ORG_IZG	KSP1	KTKP	KFRM	PRGRSPEC	PR_NORMAL	BARCODE	NMAT_PR	KDSN	PR_DO	BS_Z	KAU_Z	ABC	PLZAKUP	FIO_R	DATE_R	PR_CH_NDOC	KTKP_VP	KMAT_GOST1	KMAT_GOST2	KMAT_GOST3	PRC_TOLR	KTTII	UNTTII	FIO_O	DATE_K	STDCURR	FIO_D	DATE_D	HEIGHT	LENGHT	WIDTH	MASS	DENSITY	PRIORITET	KMPS1	NZAPPK	PCIKP	PCIKM	KEEP_TIME	KOLMPR	PLPRV	PLPRVDATA	KFPU	VZAK	RZAP1D	UNKSM	KGKD
//        W10000000045202	Технічне обслуговування та ремонтування інших автотранспортних засобів	45.20.2	45.20.2 Технічне обслуговування та ремонтування інших автотранспортних засобів	45.20.2 Технічне обслуговування та ремонтування інших автотранспортних засобів	0	W100000000     	Б	999	999	999	1.000000	1.000000	999	1.000000	1.000000	999	1.000000	1.000000	999		               	NULL	NULL	 	NULL	NULL	NULL	NULL	NULL			                    	          	NULL	NULL	УКР  	364/6     	NULL	0	NULL	0.00	0.00	NULL	 	NULL	0	0.000000	0.000000	0.0	 	NULL				NULL	NULL	NULL	     	   	    	   	0.0000	0.0000	0.0000	0.0000	 				NULL	NULL	0	0	NULL	  	0	 			0	 	NULL	NULL	 	 		NULL	 	NULL	               	               	               	0.0		0		NULL	 	ФУРМАН	2013-10-23 12:04:15.000	0.000000	0.000000	0.000000	0.000000	0.000000	0	 	0.000000	0	0	0	0.000000	 	0	NULL	0.000000	0.000000	21162




//UPDATE KSM
//        SET KMAT = <KMAT, char(15),>,NMAT = <NMAT, varchar(200),>,NAIMKM_S = <NAIMKM_S, varchar(30),>,N_RES = <N_RES, varchar(254),>,N_RES_DOC = <N_RES_DOC, varchar(254),>
//        ,PRNAIM = <PRNAIM, smallint,>,SKM = <SKM, char(15),>,KKST = <KKST, char(1),>,EDI = <EDI, smallint,>,EDI_NORM = <EDI_NORM, smallint,>,EDI_NORMP = <EDI_NORMP, smallint,>
//      ,KPER_NORM = <KPER_NORM, decimal(14,6),>,KPER_N_I = <KPER_N_I, decimal(14,6),>,EDI2 = <EDI2, smallint,>,KPER2 = <KPER2, decimal(14,6),>,KPER2_I = <KPER2_I, decimal(14,6),>
//        ,EDI3 = <EDI3, smallint,>,KPER3 = <KPER3, decimal(14,6),>,KPER3_I = <KPER3_I, decimal(14,6),>,EDI_NKALK = <EDI_NKALK, smallint,>,BCH_SIZE = <BCH_SIZE, varchar(40),>
//        ,KMAT_MAT1 = <KMAT_MAT1, char(15),>,NMAT_MAT1 = <NMAT_MAT1, varchar,>,KMAT_MAT2 = <KMAT_MAT2, char(15),>,N1_MAT = <N1_MAT, char(1),>,RESGZ = <RESGZ, varchar(40),>
//        ,MODELST = <MODELST, varchar(25),>,KODZAG = <KODZAG, decimal(10,0),>,KP001 = <KP001, char(10),>,NUMIOT = <NUMIOT, char(10),>,GOST = <GOST, varchar(50),>
//        ,ARTIKUL = <ARTIKUL, varchar(30),>,RN_SERT = <RN_SERT, char(20),>,UN_SERT = <UN_SERT, char(10),>,D_SERT = <D_SERT, datetime,>,KSKL = <KSKL, int,>
//        ,KBLS = <KBLS, char(5),>,BS = <BS, char(10),>,KAU = <KAU, varchar(12),>,PR_OTH = <PR_OTH, smallint,>,KMAT_OTH = <KMAT_OTH, char(15),>
//        ,CENA_PL = <CENA_PL, decimal(12,2),>,CENA_PLU = <CENA_PLU, decimal(12,2),>,DATECENPL = <DATECENPL, datetime,>,PR_TPR = <PR_TPR, char(1),>,CEH_N = <CEH_N, int,>
//        ,PCIK = <PCIK, smallint,>,KOLMP = <KOLMP, decimal(13,6),>,NZAPK = <NZAPK, decimal(13,6),>,NZAPD = <NZAPD, decimal(5,1),>,PR_CESZ = <PR_CESZ, char(1),>
//        ,KCESZ = <KCESZ, char(2),>,OLDKMAT = <OLDKMAT, varchar(30),>,OLDKMAT1 = <OLDKMAT1, varchar(30),>,OLDKMAT2 = <OLDKMAT2, varchar(254),>,KMATGP_O = <KMATGP_O, char(15),>
//        ,KMATGP_O1 = <KMATGP_O1, char(15),>,KMAT_KALK = <KMAT_KALK, char(15),>,KKS1 = <KKS1, char(5),>,KKS2 = <KKS2, char(3),>,KKS3 = <KKS3, char(4),>
//        ,KSBG = <KSBG, char(3),>,KF1 = <KF1, decimal(11,4),>,KF2 = <KF2, decimal(11,4),>,KF3 = <KF3, decimal(11,4),>,KF4 = <KF4, decimal(11,4),>,PR_DM = <PR_DM, char(1),>
//        ,COMM = <COMM, varchar(35),>,KOKP = <KOKP, varchar(15),>,KTNVED = <KTNVED, varchar(12),>,KMATGP_PMZ = <KMATGP_PMZ, char(15),>,HKMAT = <HKMAT, varchar,>
//      ,ORG_IZG = <ORG_IZG, int,>,KSP1 = <KSP1, smallint,>,KTKP = <KTKP, char(2),>,KFRM = <KFRM, char(2),>,PRGRSPEC = <PRGRSPEC, smallint,>,PR_NORMAL = <PR_NORMAL, char(1),>
//        ,BARCODE = <BARCODE, varchar(13),>,NMAT_PR = <NMAT_PR, varchar(30),>,KDSN = <KDSN, smallint,>,PR_DO = <PR_DO, char(1),>,BS_Z = <BS_Z, char(10),>,KAU_Z = <KAU_Z, varchar(12),>
//        ,ABC = <ABC, char(1),>,PLZAKUP = <PLZAKUP, char(1),>,FIO_R = <FIO_R, varchar(10),>,DATE_R = <DATE_R, datetime,>,PR_CH_NDOC = <PR_CH_NDOC, char(1),>
//        ,KTKP_VP = <KTKP_VP, char(2),>,KMAT_GOST1 = <KMAT_GOST1, char(15),>,KMAT_GOST2 = <KMAT_GOST2, char(15),>,KMAT_GOST3 = <KMAT_GOST3, char(15),>,PRC_TOLR = <PRC_TOLR, decimal(4,1),>
//        ,KTTII = <KTTII, varchar(30),>,UNTTII = <UNTTII, int,>,FIO_O = <FIO_O, varchar(10),>,DATE_K = <DATE_K, datetime,>,STDCURR = <STDCURR, char(1),>,FIO_D = <FIO_D, varchar(10),>
//        ,DATE_D = <DATE_D, datetime,>,HEIGHT = <HEIGHT, decimal(13,6),>,LENGHT = <LENGHT, decimal(13,6),>,WIDTH = <WIDTH, decimal(13,6),>,MASS = <MASS, decimal(13,6),>
//        ,DENSITY = <DENSITY, decimal(13,6),>,PRIORITET = <PRIORITET, smallint,>,KMPS1 = <KMPS1, char(1),>,NZAPPK = <NZAPPK, decimal(13,6),>,PCIKP = <PCIKP, smallint,>,PCIKM = <PCIKM, smallint,>
//      ,KEEP_TIME = <KEEP_TIME, smallint,>,KOLMPR = <KOLMPR, decimal(13,6),>,PLPRV = <PLPRV, char(1),>,PLPRVDATA = <PLPRVDATA, smallint,>,KFPU = <KFPU, varchar(3),>
//        ,VZAK = <VZAK, decimal(13,6),>,RZAP1D = <RZAP1D, decimal(13,6),>,UNKSM = <UNKSM, decimal(10,0),>,KGKD = <KGKD, char(6),>
//        WHERE <Условия поиска,,>



