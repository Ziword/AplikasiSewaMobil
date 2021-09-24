/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainMenu;

import Starting.LoginForm;
import xCustomDialog.dialog_absenberhasil;
import xCustomDialog.dialog_logout;
import xCustomDialog.dialog_pendaftaranok;
import xCustomDialog.punten_absen;
import Starting.RegisterForm;
import Starting.koneksidb;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import keeptoo.Drag;
import xCustomDialog.dialog_berhasildisave;
import xCustomDialog.dialog_writingdatabarangberhasil;
import xCustomDialog.dialog_writingdatabaranggagal;
import xCustomDialog.punten_dbkosong;
import xCustomDialog.punten_dbkosong1;
import xCustomDialog.punten_isikodedulu;
import xCustomDialog.punten_jumlahsudah0;
import xCustomDialog.punten_pilihtabeldulu;
import xCustomDialog.punten_stokkosong;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author henry
 */
public class landingmenu extends javax.swing.JFrame {

    /*Import yang harys ada ketika
    mengkoneksikan ke database*/
    Connection c; //konek DB
    Statement s; //Eksekusi Query
    ResultSet rs; //Menampung hasil Query
    PreparedStatement ps;
    
    //variabel
    String sql;
    
    /**
     * Creates new form RegisterForm
     */
    private String nama, alamat, kontak, username, namauser, sessionKtp;
    String statusabsen = "";
    int i = 0;
    int profitz = 0;
    String waktuy = new SimpleDateFormat("dd-MM-yyyy hh_mm_ss").format(new java.util.Date());
    String waktux = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date());
    String waktudate = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
    String waktujam = new SimpleDateFormat("hh:mm:ss").format(new java.util.Date());
    DefaultTableModel tableModel;
    
    public void tampilabsen(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("No");
        tableModel.addColumn("Tanggal");
        tableModel.addColumn("No KTP");
        tableModel.addColumn("Nama Petugas");
        tableModel.addColumn("Jam");
        try {
            sql = "select pr.ABSEN_TANGGAL, p.NOKTP, p.nama_petugas, pr.absen_jam from petugas p, persons pr where p.NOKTP = pr.NOKTP";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            int n = 1;
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    n,
                    rs.getString(4),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(1)
                });
                n++;
            }
            
            tabelpresensi.setModel(tableModel);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data tidak ada");
        }
    }
    
    public void tampilMobil(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("Nomor STNK");
        tableModel.addColumn("Nomor Plat");
        tableModel.addColumn("Jenis Mobil");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Merk Mobil");
        tableModel.addColumn("Harga Sewa");
        try {
            sql = "select * from mobil";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                });
            }
            
            tabelMobil.setModel(tableModel);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data tidak ada");
        }
    }
    
    public void tampilMobilrd(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("STNK");
        tableModel.addColumn("No Plat");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Merk Mobil");
        tableModel.addColumn("Status");
        try {
            sql = "Select * from mobil where Status = 'RD'";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString("NOSTNK"),
                    rs.getString("PLAT_NO"),
                    rs.getString("NAMA_MOBIL"),
                    rs.getString("MERK_MOBIL"),
                    rs.getString("STATUS"),
                });
            }
            
            tbMblrd.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void tampilMobilmt(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("STNK");
        tableModel.addColumn("No Plat");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Merk Mobil");
        tableModel.addColumn("Status");
        try {
            sql = "Select * from mobil where Status = 'MT'";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString("NOSTNK"),
                    rs.getString("PLAT_NO"),
                    rs.getString("NAMA_MOBIL"),
                    rs.getString("MERK_MOBIL"),
                    rs.getString("STATUS"),
                });
            }
            
            tbmblMt.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilVmblrd(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("No STNK");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Merk Mobil");
        tableModel.addColumn("Plat No");
        tableModel.addColumn("Harga Sewa");
        tableModel.addColumn("Status");
        try {
            sql = "Select * from mobil where Status = 'RD'";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString("NOSTNK"),
                    rs.getString("NAMA_MOBIL"),
                    rs.getString("MERK_MOBIL"),
                    rs.getString("PLAT_NO"),
                    rs.getString("HARGA_SEWA"),
                    rs.getString("STATUS"),
                });
            }
            
            tbViewmblrd.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilVmblrt(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("No STNK");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Merk Mobil");
        tableModel.addColumn("Plat No");
        tableModel.addColumn("Harga Sewa");
        tableModel.addColumn("Status");
        try {
            sql = "Select * from mobil where Status = 'RT'";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString("NOSTNK"),
                    rs.getString("NAMA_MOBIL"),
                    rs.getString("MERK_MOBIL"),
                    rs.getString("PLAT_NO"),
                    rs.getString("HARGA_SEWA"),
                    rs.getString("STATUS"),
                });
            }
            
            tbVmblrt.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilVmblsewa(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("No STNK");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Merk Mobil");
        tableModel.addColumn("Plat No");
        tableModel.addColumn("Harga Sewa");
        tableModel.addColumn("Status");
        try {
            sql = "Select * from mobil where Status = 'SW'";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString("NOSTNK"),
                    rs.getString("NAMA_MOBIL"),
                    rs.getString("MERK_MOBIL"),
                    rs.getString("PLAT_NO"),
                    rs.getString("HARGA_SEWA"),
                    rs.getString("STATUS"),
                });
            }
            
            tbvMblsewa.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilMobilrdsewa(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        tableModel.addColumn("NO STNK");
        tableModel.addColumn("No Plat");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Merk Mobil");
        tableModel.addColumn("Harga Sewa");
        try {
            sql = "Select * from mobil where Status = 'RD'";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString("NOSTNK"),
                    rs.getString("PLAT_NO"),
                    rs.getString("NAMA_MOBIL"),
                    rs.getString("MERK_MOBIL"),
                    rs.getString("HARGA_SEWA"),
                });
            }
            
            tblmobilsewa.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilMblreturn(){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        tableModel.addColumn("ID Trans");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("No STNK");
        tableModel.addColumn("Plat No");
        tableModel.addColumn("Nama Penyewa");
        tableModel.addColumn("No KTP");
        tableModel.addColumn("Nama Petugas");
        tableModel.addColumn("TGL Sewa");
        tableModel.addColumn("TGL Kembali");
        try {
            sql = "select ds.ID_TRANSAKSI, mb.NAMA_MOBIL, mb.NOSTNK, mb.PLAT_NO, py.NAMA_PENYEWA, py.NOKTP_PENYEWA, pt.NAMA_PETUGAS, ds.TANGGAL_SEWA, "
                    + "ds.TANGGAL_KEMBALI from petugas pt, penyewa py, detil_sewa ds, mobil mb where ds.NOKTP_PENYEWA = py.NOKTP_PENYEWA "
                    + "AND ds.NOSTNK = mb.NOSTNK and pt.NOKTP = ds.NOKTP and ds.STATUS_SEWA = 'SW' ";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                });
            }
            
            tblSewaactive.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilLaporan(String data){
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        tableModel.addColumn("No");
        tableModel.addColumn("ID Transaksi");
        tableModel.addColumn("Nama Mobil");
        tableModel.addColumn("Nama Petugas");
        tableModel.addColumn("Nama Penyewa");
        tableModel.addColumn("Lama Sewa");
        tableModel.addColumn("Biaya Sewa");
        
        try {
            sql = "select ds.ID_TRANSAKSI, mb.nama_mobil, pt.nama_petugas, py.nama_penyewa, "
                    + "DATEDIFF(ds.TANGGAL_KEMBALI, ds.TANGGAL_SEWA) \"Lama Sewa\", ds.biaya_sewa "
                    + "from petugas pt, detil_sewa ds, penyewa py, mobil mb where pt.NOKTP = ds.NOKTP "
                    + "and py.NOKTP_PENYEWA = ds.NOKTP_PENYEWA and mb.NOSTNK = ds.NOSTNK and ds.STATUS_SEWA = 'BK' "
                    + "and ds.TANGGAL_KEMBALI like '"+data+"'";
            c = (Connection) koneksidb.koneksi();
            s = c.createStatement();
            rs = s.executeQuery(sql);
            int no = 1;
            while (rs.next()) {
                tableModel.addRow(new Object[]
                {
                    String.valueOf(no),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                });
                no++;
            }
            
            tabelsheetmonthly.setModel(tableModel);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tutuplogout() {
        this.dispose();
    }
    
        public void KoneksiAwal(String user_id) {

             Connection con = null;
             try {
                 Class.forName("com.mysql.cj.jdbc.Driver");
                 con = DriverManager.getConnection("jdbc:mysql://localhost/pbo_uas?useTimezone=true&serverTimezone=UTC", "root", "");
                 Statement stat = con.createStatement();
                 ResultSet rs = stat.executeQuery("SELECT noktp, nama_petugas, username, kontak, alamat_petugas FROM petugas WHERE noktp=" + user_id);
                 while (rs.next()) {
                     sessionKtp = rs.getString("noktp");
                     String nama = rs.getString("nama_petugas");
                     String username = rs.getString("username");
                     String kontak = rs.getString("kontak");
                     String alamat = rs.getString("alamat_petugas");
                     System.out.println(sessionKtp);
                     System.out.println(nama);
                     System.out.println(username);
                     System.out.println(kontak);
                     System.out.println(alamat);
                     System.out.println("Berhasil Dicetak");
                     setWelcome(nama, kontak, alamat);
                     yaccount.setText(nama);
                     outputNoktp.setText(sessionKtp);
                     
                 }

                 System.out.println("Koneksi ke Database (Fetching) Berhasil");
             } catch (Exception ex) {
                 System.out.println("Gagal Koneksi (Fetching)ke Database");
                 System.out.println(ex.getMessage());
             }
         }
           
        public void setWelcome(String nama, String kontak, String alamat) {
             this.nama = nama;
             this.kontak = kontak;
             this.alamat = alamat;

             outputNama.setText(nama);
             outputkontak.setText(kontak);
             outputAlamat.setText(alamat);

         }
    
        public void Timer() {
        Timer t2 = new Timer();
        t2.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                String waktunow = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date());
                timenow.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date()));
                timenow1.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date()));
                timenow2.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date()));
                timenow3.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date()));

            }
        }, 0, 1000);
        String waktux = new SimpleDateFormat("ssddMMyyyyhhmm").format(new java.util.Date());
    }

        public landingmenu(String a) {
        initComponents();
        Timer();
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(255, 255, 255, 255));
        tabelpresensi.getTableHeader().setFont(new Font("Montserrat", Font.PLAIN, 12));
        tabelpresensi.getTableHeader().setOpaque(false);
        tabelpresensi.getTableHeader().setBackground(new Color(32, 136, 203));
        tabelpresensi.getTableHeader().setForeground(new Color(255, 255, 255));
        tabelpresensi.setRowHeight(25);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String waktu = String.valueOf(dtf.format(now));
        Runnable waktunow = new Runnable() {
            public void run() {
//        
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(waktunow, 0, 1, TimeUnit.SECONDS);

        this.namauser = a;
        KoneksiAwal(namauser);

        String primary = outputNoktp.getText() + waktudate;
        PreparedStatement ps;
        ResultSet rs;
        String queryx = "SELECT * FROM `persons` WHERE `ID_Absen` =?";
        try {
            System.out.println("====STATUS ABSEN====");
            ps = koneksidb.getConnection().prepareStatement(queryx);
            ps.setString(1, primary);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("User Sudah Absen");
                subpanelstatusabsen.removeAll();
                subpanelstatusabsen.repaint();
                subpanelstatusabsen.revalidate();
                subpanelstatusabsen.add(statusverified);
                subpanelstatusabsen.repaint();
                subpanelstatusabsen.revalidate();
            } else {
                System.out.println("User Belum Absen");
                subpanelstatusabsen.removeAll();
                subpanelstatusabsen.repaint();
                subpanelstatusabsen.revalidate();
                subpanelstatusabsen.add(statusunverified);
                subpanelstatusabsen.repaint();
                subpanelstatusabsen.revalidate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgTindakan = new javax.swing.ButtonGroup();
        panelmenu = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        tombollogout = new keeptoo.KButton();
        tombollihatabsensi = new keeptoo.KButton();
        tombolMobil = new keeptoo.KButton();
        tombolkembalibig = new javax.swing.JLabel();
        tombolSewa = new keeptoo.KButton();
        tombolLaporan = new keeptoo.KButton();
        panelutama = new javax.swing.JPanel();
        panelLanding = new keeptoo.KGradientPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        yaccount = new javax.swing.JLabel();
        outputNama = new javax.swing.JLabel();
        outputkontak = new javax.swing.JLabel();
        outputAlamat = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        tombolpresensi = new javax.swing.JLabel();
        outputNoktp = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        subpanelstatusabsen = new keeptoo.KGradientPanel();
        statusunverified = new keeptoo.KGradientPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        statusverified = new keeptoo.KGradientPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        laporanAbsen = new keeptoo.KGradientPanel();
        jLabel196 = new javax.swing.JLabel();
        kGradientPanel38 = new keeptoo.KGradientPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tabelpresensi = new javax.swing.JTable();
        tampilkanabsen = new javax.swing.JLabel();
        panelMobil = new keeptoo.KGradientPanel();
        kGradientPanel11 = new keeptoo.KGradientPanel();
        kGradientPanel14 = new keeptoo.KGradientPanel();
        jLabel61 = new javax.swing.JLabel();
        btnbarcodescanner2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        btninputmanual2 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        kGradientPanel15 = new keeptoo.KGradientPanel();
        tombolMt = new javax.swing.JLabel();
        kGradientPanel19 = new keeptoo.KGradientPanel();
        tombollihatbarang = new javax.swing.JLabel();
        kGradientPanel16 = new keeptoo.KGradientPanel();
        tomboleditbarang1 = new javax.swing.JLabel();
        panelEditmobil = new keeptoo.KGradientPanel();
        kGradientPanel17 = new keeptoo.KGradientPanel();
        kGradientPanel18 = new keeptoo.KGradientPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelMobil = new javax.swing.JTable();
        inputNostnk = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        inputPlat = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        inputmerkmbl = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        inputHrgsewambl = new javax.swing.JTextField();
        btninputkedatabase = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        btndeleteitem = new javax.swing.JLabel();
        btngetselected = new javax.swing.JLabel();
        btnedititem = new javax.swing.JLabel();
        inputNamambl = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        cbJenismbl = new javax.swing.JComboBox<>();
        panelLihatmobil = new keeptoo.KGradientPanel();
        kGradientPanel20 = new keeptoo.KGradientPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbVmblrt = new javax.swing.JTable();
        jLabel69 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbViewmblrd = new javax.swing.JTable();
        jLabel100 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbvMblsewa = new javax.swing.JTable();
        kGradientPanel21 = new keeptoo.KGradientPanel();
        kGradientPanel22 = new keeptoo.KGradientPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        panelMT = new keeptoo.KGradientPanel();
        jPanel2 = new javax.swing.JPanel();
        yaMobil = new javax.swing.JLabel();
        outputPlmt = new javax.swing.JLabel();
        outputMrkmt = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        sendMt = new javax.swing.JLabel();
        outputStnkmt = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        rbMt = new javax.swing.JRadioButton();
        rbMtFinished = new javax.swing.JRadioButton();
        rbRetire = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputKeterangan = new javax.swing.JTextArea();
        jLabel94 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbmblMt = new javax.swing.JTable();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbMblrd = new javax.swing.JTable();
        panelSewa = new keeptoo.KGradientPanel();
        kGradientPanel12 = new keeptoo.KGradientPanel();
        kGradientPanel23 = new keeptoo.KGradientPanel();
        jLabel74 = new javax.swing.JLabel();
        btnbarcodescanner3 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        btninputmanual3 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        kGradientPanel24 = new keeptoo.KGradientPanel();
        btnSewambl = new javax.swing.JLabel();
        kGradientPanel25 = new keeptoo.KGradientPanel();
        btnReturnmbl = new javax.swing.JLabel();
        panelUsewa = new keeptoo.KGradientPanel();
        jPanel3 = new javax.swing.JPanel();
        yaMobil1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txtnoKTP = new javax.swing.JTextField();
        txtNamasewa = new javax.swing.JTextField();
        txtAlamatsewa = new javax.swing.JTextField();
        txtBarangsewa = new javax.swing.JTextField();
        yaMobil2 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        outSewaplat = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        outSewaMerk = new javax.swing.JLabel();
        outSewanama = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        outSewaharga = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        outSewatotalharga = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        txtLamasewa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSimpanpenyewa = new keeptoo.KButton();
        jLabel3 = new javax.swing.JLabel();
        outSewastnk = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        txtKontaksewa1 = new javax.swing.JTextField();
        btnCek = new keeptoo.KButton();
        jLabel99 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblmobilsewa = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        btnSewa = new javax.swing.JLabel();
        panelKembali = new keeptoo.KGradientPanel();
        kGradientPanel26 = new keeptoo.KGradientPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblSewaactive = new javax.swing.JTable();
        submitReturn = new keeptoo.KButton();
        jLabel91 = new javax.swing.JLabel();
        outReturnmbl = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        outreturnIdtrans = new javax.swing.JLabel();
        outReturnnostnk = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        kGradientPanel27 = new keeptoo.KGradientPanel();
        kGradientPanel28 = new keeptoo.KGradientPanel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        LaporanBulanan = new keeptoo.KGradientPanel();
        tampungmonthlyreport = new keeptoo.KGradientPanel();
        tampilanmonthlyreport = new keeptoo.KGradientPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        tabelsheetmonthly = new javax.swing.JTable();
        jLabel150 = new javax.swing.JLabel();
        bulandantahun = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        omsettokobulanan = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JLabel();
        emptymonthlyreport = new keeptoo.KGradientPanel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        subpanelpilihmonthlyreport = new keeptoo.KGradientPanel();
        pilihbulan = new keeptoo.KGradientPanel();
        jLabel104 = new javax.swing.JLabel();
        ubulan2 = new javax.swing.JComboBox<>();
        utahun2 = new javax.swing.JComboBox<>();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        refreshsheetmonthly = new javax.swing.JLabel();
        panelstatus = new javax.swing.JPanel();
        statusutama = new keeptoo.KGradientPanel();
        jLabel2 = new javax.swing.JLabel();
        tombolminim1 = new javax.swing.JLabel();
        tombolclose1 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        timenow = new javax.swing.JLabel();
        labelcp = new javax.swing.JLabel();
        statusutamacashier = new keeptoo.KGradientPanel();
        jLabel20 = new javax.swing.JLabel();
        tombolminim = new javax.swing.JLabel();
        tombolclose = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        timenow1 = new javax.swing.JLabel();
        statuspanelpresensi = new keeptoo.KGradientPanel();
        jLabel11 = new javax.swing.JLabel();
        tombolminim4 = new javax.swing.JLabel();
        tombolclose4 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        timenow2 = new javax.swing.JLabel();
        statuspanelfeedback = new keeptoo.KGradientPanel();
        jLabel88 = new javax.swing.JLabel();
        tombolminim2 = new javax.swing.JLabel();
        tombolclose2 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        timenow3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelmenu.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/lg.png"))); // NOI18N
        jLabel8.setText("Form Registrasi");

        tombollogout.setText("Log Out");
        tombollogout.setBorderPainted(false);
        tombollogout.setkBorderRadius(30);
        tombollogout.setkEndColor(new java.awt.Color(51, 153, 255));
        tombollogout.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        tombollogout.setkHoverForeGround(new java.awt.Color(51, 102, 255));
        tombollogout.setkHoverStartColor(new java.awt.Color(0, 153, 255));
        tombollogout.setkSelectedColor(new java.awt.Color(0, 204, 204));
        tombollogout.setkStartColor(new java.awt.Color(51, 153, 255));
        tombollogout.setOpaque(false);
        tombollogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombollogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombollogoutMouseEntered(evt);
            }
        });

        tombollihatabsensi.setText("Cek Absensi");
        tombollihatabsensi.setBorderPainted(false);
        tombollihatabsensi.setkBorderRadius(30);
        tombollihatabsensi.setkEndColor(new java.awt.Color(51, 153, 255));
        tombollihatabsensi.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        tombollihatabsensi.setkHoverForeGround(new java.awt.Color(51, 102, 255));
        tombollihatabsensi.setkHoverStartColor(new java.awt.Color(0, 153, 255));
        tombollihatabsensi.setkSelectedColor(new java.awt.Color(0, 204, 204));
        tombollihatabsensi.setkStartColor(new java.awt.Color(51, 153, 255));
        tombollihatabsensi.setOpaque(false);
        tombollihatabsensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombollihatabsensiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombollihatabsensiMouseEntered(evt);
            }
        });

        tombolMobil.setText("Mobil");
        tombolMobil.setBorderPainted(false);
        tombolMobil.setkBorderRadius(30);
        tombolMobil.setkEndColor(new java.awt.Color(51, 153, 255));
        tombolMobil.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        tombolMobil.setkHoverForeGround(new java.awt.Color(0, 51, 255));
        tombolMobil.setkHoverStartColor(new java.awt.Color(0, 153, 255));
        tombolMobil.setkSelectedColor(new java.awt.Color(0, 204, 204));
        tombolMobil.setkStartColor(new java.awt.Color(51, 153, 255));
        tombolMobil.setOpaque(false);
        tombolMobil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolMobilMouseClicked(evt);
            }
        });
        tombolMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolMobilActionPerformed(evt);
            }
        });

        tombolkembalibig.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tombolkembalibig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-home-64.png"))); // NOI18N
        tombolkembalibig.setText(" Home");
        tombolkembalibig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolkembalibig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolkembalibigMouseClicked(evt);
            }
        });

        tombolSewa.setText("Sewa");
        tombolSewa.setBorderPainted(false);
        tombolSewa.setkBorderRadius(30);
        tombolSewa.setkEndColor(new java.awt.Color(51, 153, 255));
        tombolSewa.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        tombolSewa.setkHoverForeGround(new java.awt.Color(0, 51, 255));
        tombolSewa.setkHoverStartColor(new java.awt.Color(0, 153, 255));
        tombolSewa.setkSelectedColor(new java.awt.Color(0, 204, 204));
        tombolSewa.setkStartColor(new java.awt.Color(51, 153, 255));
        tombolSewa.setOpaque(false);
        tombolSewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolSewaMouseClicked(evt);
            }
        });

        tombolLaporan.setText("Laporan");
        tombolLaporan.setBorderPainted(false);
        tombolLaporan.setkBorderRadius(30);
        tombolLaporan.setkEndColor(new java.awt.Color(51, 153, 255));
        tombolLaporan.setkHoverEndColor(new java.awt.Color(0, 204, 204));
        tombolLaporan.setkHoverForeGround(new java.awt.Color(51, 102, 255));
        tombolLaporan.setkHoverStartColor(new java.awt.Color(0, 153, 255));
        tombolLaporan.setkSelectedColor(new java.awt.Color(0, 204, 204));
        tombolLaporan.setkStartColor(new java.awt.Color(51, 153, 255));
        tombolLaporan.setOpaque(false);
        tombolLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolLaporanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombolLaporanMouseEntered(evt);
            }
        });
        tombolLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolLaporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelmenuLayout = new javax.swing.GroupLayout(panelmenu);
        panelmenu.setLayout(panelmenuLayout);
        panelmenuLayout.setHorizontalGroup(
            panelmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelmenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tombolkembalibig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelmenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tombolLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tombolSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tombolMobil, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tombollihatabsensi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tombollogout, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelmenuLayout.setVerticalGroup(
            panelmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelmenuLayout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(tombolSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tombolMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tombolLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tombollihatabsensi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tombollogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(tombolkembalibig)
                .addGap(22, 22, 22))
        );

        getContentPane().add(panelmenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelutama.setBackground(new java.awt.Color(255, 204, 204));
        panelutama.setLayout(new java.awt.CardLayout());

        panelLanding.setkBorderRadius(0);
        panelLanding.setkEndColor(new java.awt.Color(137, 255, 253));
        panelLanding.setkGradientFocus(2000);
        panelLanding.setkStartColor(new java.awt.Color(97, 144, 232));

        jLabel18.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Unit Usaha Universitas Dinamika");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        jPanel1.setOpaque(false);

        yaccount.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        yaccount.setForeground(new java.awt.Color(51, 51, 51));
        yaccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user264px.png"))); // NOI18N
        yaccount.setText("Your account");

        outputNama.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outputNama.setForeground(new java.awt.Color(51, 51, 51));
        outputNama.setText("Nama Pegawai");
        outputNama.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        outputkontak.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outputkontak.setForeground(new java.awt.Color(51, 51, 51));
        outputkontak.setText("Kontak");
        outputkontak.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        outputAlamat.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outputAlamat.setForeground(new java.awt.Color(51, 51, 51));
        outputAlamat.setText("Alamat");
        outputAlamat.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel16.setText("Nama Pegawai");

        jLabel21.setText("Kontak ");

        jLabel23.setText("Alamat");

        jLabel28.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("untuk melakukan absen");

        jLabel29.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("Silahkan klik tombol dibawah");

        tombolpresensi.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        tombolpresensi.setForeground(new java.awt.Color(51, 51, 51));
        tombolpresensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-next-page-60.png"))); // NOI18N
        tombolpresensi.setText("presensi");
        tombolpresensi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolpresensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolpresensiMouseClicked(evt);
            }
        });

        outputNoktp.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outputNoktp.setForeground(new java.awt.Color(51, 51, 51));
        outputNoktp.setText("No KTP");
        outputNoktp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel24.setText("No KTP");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel23)
                                    .addComponent(outputNama, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                    .addComponent(outputkontak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(outputAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel24)
                                    .addComponent(outputNoktp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(tombolpresensi)))
                        .addGap(0, 103, Short.MAX_VALUE))
                    .addComponent(yaccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yaccount)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputNoktp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputNama)
                .addGap(7, 7, 7)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputkontak)
                .addGap(7, 7, 7)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputAlamat)
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tombolpresensi)
                .addGap(24, 24, 24))
        );

        subpanelstatusabsen.setkBorderRadius(30);
        subpanelstatusabsen.setkEndColor(new java.awt.Color(255, 255, 255));
        subpanelstatusabsen.setkStartColor(new java.awt.Color(255, 255, 255));
        subpanelstatusabsen.setOpaque(false);
        subpanelstatusabsen.setLayout(new java.awt.CardLayout());

        statusunverified.setkBorderRadius(30);
        statusunverified.setkEndColor(new java.awt.Color(255, 255, 255));
        statusunverified.setkStartColor(new java.awt.Color(255, 255, 255));
        statusunverified.setOpaque(false);

        jLabel39.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel39.setText("Anda Belum Melakukan Absensi");

        jLabel40.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-delete-50.png"))); // NOI18N
        jLabel40.setText("Belum Terinput");

        jLabel41.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(51, 51, 51));
        jLabel41.setText("Status Absensi ");

        jLabel42.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel42.setText("Hari Ini ");

        javax.swing.GroupLayout statusunverifiedLayout = new javax.swing.GroupLayout(statusunverified);
        statusunverified.setLayout(statusunverifiedLayout);
        statusunverifiedLayout.setHorizontalGroup(
            statusunverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusunverifiedLayout.createSequentialGroup()
                .addGroup(statusunverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusunverifiedLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(statusunverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(statusunverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel39)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusunverifiedLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel42)
                                    .addGap(106, 106, 106)))
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(statusunverifiedLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel40)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        statusunverifiedLayout.setVerticalGroup(
            statusunverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusunverifiedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addGap(18, 18, 18)
                .addComponent(jLabel40)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subpanelstatusabsen.add(statusunverified, "card2");

        statusverified.setkBorderRadius(30);
        statusverified.setkEndColor(new java.awt.Color(255, 255, 255));
        statusverified.setkStartColor(new java.awt.Color(255, 255, 255));
        statusverified.setOpaque(false);

        jLabel35.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel35.setText("Anda Sudah Melakukan Absensi");

        jLabel36.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-ok-50.png"))); // NOI18N
        jLabel36.setText("Terverifikasi");

        jLabel37.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 51, 51));
        jLabel37.setText("Status Absensi ");

        jLabel38.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel38.setText("Hari Ini ");

        javax.swing.GroupLayout statusverifiedLayout = new javax.swing.GroupLayout(statusverified);
        statusverified.setLayout(statusverifiedLayout);
        statusverifiedLayout.setHorizontalGroup(
            statusverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusverifiedLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(statusverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addGroup(statusverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusverifiedLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel36))
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusverifiedLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel38)
                .addGap(130, 130, 130))
        );
        statusverifiedLayout.setVerticalGroup(
            statusverifiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusverifiedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addContainerGap())
        );

        subpanelstatusabsen.add(statusverified, "card4");

        jLabel93.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(51, 51, 51));
        jLabel93.setText("Terima Kasih , Anda Login sebagai");

        jLabel166.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel166.setForeground(new java.awt.Color(51, 51, 51));
        jLabel166.setText("Selamat Datang di Aplikasi Sewa Mobil ");

        jLabel167.setFont(new java.awt.Font("Montserrat", 0, 36)); // NOI18N
        jLabel167.setForeground(new java.awt.Color(51, 51, 51));
        jLabel167.setText("Shuki Mobil");

        jLabel168.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel168.setForeground(new java.awt.Color(51, 51, 51));
        jLabel168.setText("-D3 SI 2020-");

        javax.swing.GroupLayout panelLandingLayout = new javax.swing.GroupLayout(panelLanding);
        panelLanding.setLayout(panelLandingLayout);
        panelLandingLayout.setHorizontalGroup(
            panelLandingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLandingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelLandingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLandingLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(panelLandingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subpanelstatusabsen, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel166)
                            .addComponent(jLabel167)))
                    .addGroup(panelLandingLayout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel168)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLandingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLandingLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jLabel93)
                    .addContainerGap(713, Short.MAX_VALUE)))
        );
        panelLandingLayout.setVerticalGroup(
            panelLandingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLandingLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(panelLandingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLandingLayout.createSequentialGroup()
                        .addComponent(subpanelstatusabsen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jLabel166)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel167)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel168)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLandingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLandingLayout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jLabel93)
                    .addContainerGap(517, Short.MAX_VALUE)))
        );

        panelutama.add(panelLanding, "card3");

        laporanAbsen.setkBorderRadius(0);
        laporanAbsen.setkEndColor(new java.awt.Color(137, 255, 253));
        laporanAbsen.setkGradientFocus(1000);
        laporanAbsen.setkStartColor(new java.awt.Color(97, 144, 232));

        jLabel196.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        jLabel196.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-information-75.png"))); // NOI18N
        jLabel196.setText("Panel Lihat Laporan Absen");

        kGradientPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 153), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 153, 153)));
        kGradientPanel38.setkBorderRadius(0);
        kGradientPanel38.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel38.setkGradientFocus(1000);
        kGradientPanel38.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel38.setkTransparentControls(false);
        kGradientPanel38.setOpaque(false);

        tabelpresensi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Tanggal", "No KTP", "Nama Petugas", "Jam"
            }
        ));
        tabelpresensi.setFocusable(false);
        tabelpresensi.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelpresensi.setRowHeight(25);
        tabelpresensi.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tabelpresensi.setShowVerticalLines(false);
        tabelpresensi.getTableHeader().setReorderingAllowed(false);
        jScrollPane15.setViewportView(tabelpresensi);

        javax.swing.GroupLayout kGradientPanel38Layout = new javax.swing.GroupLayout(kGradientPanel38);
        kGradientPanel38.setLayout(kGradientPanel38Layout);
        kGradientPanel38Layout.setHorizontalGroup(
            kGradientPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
                .addContainerGap())
        );
        kGradientPanel38Layout.setVerticalGroup(
            kGradientPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addContainerGap())
        );

        tampilkanabsen.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        tampilkanabsen.setForeground(new java.awt.Color(51, 51, 51));
        tampilkanabsen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-next-page-60.png"))); // NOI18N
        tampilkanabsen.setText("Tampilkan");
        tampilkanabsen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tampilkanabsen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tampilkanabsenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout laporanAbsenLayout = new javax.swing.GroupLayout(laporanAbsen);
        laporanAbsen.setLayout(laporanAbsenLayout);
        laporanAbsenLayout.setHorizontalGroup(
            laporanAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanAbsenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(laporanAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(laporanAbsenLayout.createSequentialGroup()
                        .addComponent(jLabel196)
                        .addGap(44, 44, 44)
                        .addComponent(tampilkanabsen))
                    .addComponent(kGradientPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        laporanAbsenLayout.setVerticalGroup(
            laporanAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanAbsenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(laporanAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel196)
                    .addComponent(tampilkanabsen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelutama.add(laporanAbsen, "card12");

        panelMobil.setkBorderRadius(0);
        panelMobil.setkEndColor(new java.awt.Color(137, 255, 253));
        panelMobil.setkGradientFocus(1000);
        panelMobil.setkStartColor(new java.awt.Color(97, 144, 232));
        panelMobil.setkTransparentControls(false);

        kGradientPanel11.setkBorderRadius(30);
        kGradientPanel11.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel11.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel11.setOpaque(false);

        kGradientPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        kGradientPanel14.setkBorderRadius(30);
        kGradientPanel14.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel14.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel14.setOpaque(false);

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(51, 51, 51));
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-information-75.png"))); // NOI18N
        jLabel61.setText("Information");

        btnbarcodescanner2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnbarcodescanner2.setForeground(new java.awt.Color(51, 51, 51));
        btnbarcodescanner2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-more-details-80.png"))); // NOI18N
        btnbarcodescanner2.setText("Lihat Mobil");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 153, 153));
        jLabel22.setText("Untuk melihat status mobil pada database");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(153, 153, 153));
        jLabel46.setText("Baik status mobil sewa/maintainance");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(153, 153, 153));
        jLabel51.setText("Tidak merubah kondisi/status mobil");

        btninputmanual2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btninputmanual2.setForeground(new java.awt.Color(51, 51, 51));
        btninputmanual2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-compose-80.png"))); // NOI18N
        btninputmanual2.setText("Input Mobil");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(153, 153, 153));
        jLabel60.setText("Untuk mengubah data Mobil di database");

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(153, 153, 153));
        jLabel72.setText("Seperti nama, merk, dan harga sewa");

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(153, 153, 153));
        jLabel73.setText("Tidak merubah kondisi/status mobil");

        javax.swing.GroupLayout kGradientPanel14Layout = new javax.swing.GroupLayout(kGradientPanel14);
        kGradientPanel14.setLayout(kGradientPanel14Layout);
        kGradientPanel14Layout.setHorizontalGroup(
            kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel14Layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(kGradientPanel14Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel46)
                    .addComponent(jLabel51)
                    .addComponent(btnbarcodescanner2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60)
                    .addComponent(jLabel72)
                    .addComponent(jLabel73)
                    .addComponent(btninputmanual2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel14Layout.setVerticalGroup(
            kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61)
                .addGap(18, 18, 18)
                .addComponent(btnbarcodescanner2)
                .addGap(29, 29, 29)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btninputmanual2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel73)
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout kGradientPanel11Layout = new javax.swing.GroupLayout(kGradientPanel11);
        kGradientPanel11.setLayout(kGradientPanel11Layout);
        kGradientPanel11Layout.setHorizontalGroup(
            kGradientPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        kGradientPanel11Layout.setVerticalGroup(
            kGradientPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addContainerGap())
        );

        kGradientPanel15.setkBorderRadius(30);
        kGradientPanel15.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel15.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel15.setOpaque(false);

        tombolMt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tombolMt.setForeground(new java.awt.Color(51, 51, 51));
        tombolMt.setIcon(new javax.swing.ImageIcon("D:\\Users\\Ziword\\Documents\\NetBeansProjects.Backup\\NetBeansProjects\\Tugas Besar\\src\\image\\maintenance_80px.png")); // NOI18N
        tombolMt.setText("Maintenance");
        tombolMt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolMt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolMtMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel15Layout = new javax.swing.GroupLayout(kGradientPanel15);
        kGradientPanel15.setLayout(kGradientPanel15Layout);
        kGradientPanel15Layout.setHorizontalGroup(
            kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel15Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(tombolMt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        kGradientPanel15Layout.setVerticalGroup(
            kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel15Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(tombolMt)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        kGradientPanel19.setkBorderRadius(30);
        kGradientPanel19.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel19.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel19.setOpaque(false);
        kGradientPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kGradientPanel19MouseClicked(evt);
            }
        });

        tombollihatbarang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tombollihatbarang.setForeground(new java.awt.Color(51, 51, 51));
        tombollihatbarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-more-details-80.png"))); // NOI18N
        tombollihatbarang.setText("Lihat Mobil");
        tombollihatbarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombollihatbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombollihatbarangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel19Layout = new javax.swing.GroupLayout(kGradientPanel19);
        kGradientPanel19.setLayout(kGradientPanel19Layout);
        kGradientPanel19Layout.setHorizontalGroup(
            kGradientPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel19Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(tombollihatbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        kGradientPanel19Layout.setVerticalGroup(
            kGradientPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel19Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(tombollihatbarang)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        kGradientPanel16.setkBorderRadius(30);
        kGradientPanel16.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel16.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel16.setOpaque(false);

        tomboleditbarang1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tomboleditbarang1.setForeground(new java.awt.Color(51, 51, 51));
        tomboleditbarang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-compose-80.png"))); // NOI18N
        tomboleditbarang1.setText("Edit Mobil");
        tomboleditbarang1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tomboleditbarang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tomboleditbarang1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel16Layout = new javax.swing.GroupLayout(kGradientPanel16);
        kGradientPanel16.setLayout(kGradientPanel16Layout);
        kGradientPanel16Layout.setHorizontalGroup(
            kGradientPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel16Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(tomboleditbarang1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        kGradientPanel16Layout.setVerticalGroup(
            kGradientPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel16Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(tomboleditbarang1)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelMobilLayout = new javax.swing.GroupLayout(panelMobil);
        panelMobil.setLayout(panelMobilLayout);
        panelMobilLayout.setHorizontalGroup(
            panelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMobilLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kGradientPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kGradientPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(kGradientPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        panelMobilLayout.setVerticalGroup(
            panelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMobilLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(panelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kGradientPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82)
                .addComponent(kGradientPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMobilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        panelutama.add(panelMobil, "card2");

        panelEditmobil.setkBorderRadius(0);
        panelEditmobil.setkEndColor(new java.awt.Color(137, 255, 253));
        panelEditmobil.setkGradientFocus(1000);
        panelEditmobil.setkStartColor(new java.awt.Color(97, 144, 232));
        panelEditmobil.setkTransparentControls(false);

        kGradientPanel17.setkBorderRadius(30);
        kGradientPanel17.setkEndColor(new java.awt.Color(255, 204, 102));
        kGradientPanel17.setkStartColor(new java.awt.Color(255, 204, 102));
        kGradientPanel17.setOpaque(false);

        kGradientPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        kGradientPanel18.setkBorderRadius(30);
        kGradientPanel18.setkEndColor(new java.awt.Color(255, 204, 102));
        kGradientPanel18.setkStartColor(new java.awt.Color(255, 204, 102));
        kGradientPanel18.setOpaque(false);

        tabelMobil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor STNK", "Nomor Plat", "Jenis Mobil", "Nama Mobil", "Merk Mobil ", "Harga Sewa "
            }
        ));
        tabelMobil.setFocusable(false);
        tabelMobil.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelMobil.setRowHeight(25);
        tabelMobil.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tabelMobil.setShowVerticalLines(false);
        tabelMobil.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabelMobil);

        javax.swing.GroupLayout kGradientPanel18Layout = new javax.swing.GroupLayout(kGradientPanel18);
        kGradientPanel18.setLayout(kGradientPanel18Layout);
        kGradientPanel18Layout.setHorizontalGroup(
            kGradientPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );
        kGradientPanel18Layout.setVerticalGroup(
            kGradientPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );

        javax.swing.GroupLayout kGradientPanel17Layout = new javax.swing.GroupLayout(kGradientPanel17);
        kGradientPanel17.setLayout(kGradientPanel17Layout);
        kGradientPanel17Layout.setHorizontalGroup(
            kGradientPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
        );
        kGradientPanel17Layout.setVerticalGroup(
            kGradientPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );

        inputNostnk.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        inputNostnk.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        inputNostnk.setOpaque(false);

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Nomor STNK :");

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Nomor Plat :");

        inputPlat.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        inputPlat.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        inputPlat.setOpaque(false);

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Jenis Mobil :");

        inputmerkmbl.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        inputmerkmbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        inputmerkmbl.setOpaque(false);

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Merk Mobil :");

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Harga Sewa (/Hari) : Rp.");

        inputHrgsewambl.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        inputHrgsewambl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        inputHrgsewambl.setOpaque(false);

        btninputkedatabase.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        btninputkedatabase.setForeground(new java.awt.Color(255, 255, 255));
        btninputkedatabase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-plus-60.png"))); // NOI18N
        btninputkedatabase.setText("Input Ke Database");
        btninputkedatabase.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btninputkedatabase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbtninputkedatabaseMouseClicked(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Data Mobil di Database");

        btndeleteitem.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        btndeleteitem.setForeground(new java.awt.Color(255, 255, 255));
        btndeleteitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-delete-60.png"))); // NOI18N
        btndeleteitem.setText("Hapus Item Dari Database");
        btndeleteitem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btndeleteitem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndeleteitemMouseClicked(evt);
            }
        });

        btngetselected.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        btngetselected.setForeground(new java.awt.Color(255, 255, 255));
        btngetselected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-checkmark-60.png"))); // NOI18N
        btngetselected.setText("Select Item ( Get Selected Value)");
        btngetselected.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btngetselected.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngetselectedMouseClicked(evt);
            }
        });

        btnedititem.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        btnedititem.setForeground(new java.awt.Color(255, 255, 255));
        btnedititem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-edit-60.png"))); // NOI18N
        btnedititem.setText(" Edit Table Yang Dipilih");
        btnedititem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnedititem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnedititemMouseClicked(evt);
            }
        });

        inputNamambl.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        inputNamambl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        inputNamambl.setOpaque(false);

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Nama Mobil :");

        cbJenismbl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minivan", "SUV", "Sedan", "Sport" }));

        javax.swing.GroupLayout panelEditmobilLayout = new javax.swing.GroupLayout(panelEditmobil);
        panelEditmobil.setLayout(panelEditmobilLayout);
        panelEditmobilLayout.setHorizontalGroup(
            panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditmobilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelEditmobilLayout.createSequentialGroup()
                                .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputNostnk, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel66)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28))
                            .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputNamambl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditmobilLayout.createSequentialGroup()
                            .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inputmerkmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inputHrgsewambl, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel68))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditmobilLayout.createSequentialGroup()
                        .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditmobilLayout.createSequentialGroup()
                                    .addComponent(btninputkedatabase)
                                    .addGap(86, 86, 86))
                                .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btngetselected, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnedititem)
                                    .addComponent(btndeleteitem)))
                            .addComponent(cbJenismbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditmobilLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(kGradientPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEditmobilLayout.setVerticalGroup(
            panelEditmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditmobilLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kGradientPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelEditmobilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNostnk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbJenismbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNamambl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputmerkmbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputHrgsewambl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btninputkedatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btngetselected, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnedititem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btndeleteitem, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelutama.add(panelEditmobil, "card2");

        panelLihatmobil.setkBorderRadius(0);
        panelLihatmobil.setkEndColor(new java.awt.Color(137, 255, 253));
        panelLihatmobil.setkGradientFocus(1000);
        panelLihatmobil.setkStartColor(new java.awt.Color(97, 144, 232));
        panelLihatmobil.setkTransparentControls(false);

        kGradientPanel20.setkBorderRadius(30);
        kGradientPanel20.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel20.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel20.setOpaque(false);

        tbVmblrt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No STNK", "Nama Mobil", "Merk Mobil", "Plat No", "Harga Sewa", "Status"
            }
        ));
        tbVmblrt.setFocusable(false);
        tbVmblrt.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbVmblrt.setRowHeight(25);
        tbVmblrt.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tbVmblrt.setShowVerticalLines(false);
        tbVmblrt.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tbVmblrt);

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel69.setText("Data Mobil di Database");

        jLabel97.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(51, 51, 51));
        jLabel97.setText("Data Mobil Pensiun");

        jLabel98.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(51, 51, 51));
        jLabel98.setText("Data Mobil Ready");

        tbViewmblrd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No STNK", "Nama Mobil", "Merk Mobil", "Plat No", "Harga Sewa", "Status"
            }
        ));
        tbViewmblrd.setFocusable(false);
        tbViewmblrd.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbViewmblrd.setRowHeight(25);
        tbViewmblrd.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tbViewmblrd.setShowVerticalLines(false);
        tbViewmblrd.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tbViewmblrd);

        jLabel100.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(51, 51, 51));
        jLabel100.setText("Data Mobil Disewa");

        tbvMblsewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No STNK", "Nama Mobil", "Merk Mobil", "Plat No", "Harga Sewa", "Status"
            }
        ));
        tbvMblsewa.setFocusable(false);
        tbvMblsewa.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbvMblsewa.setRowHeight(25);
        tbvMblsewa.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tbvMblsewa.setShowVerticalLines(false);
        tbvMblsewa.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tbvMblsewa);

        javax.swing.GroupLayout kGradientPanel20Layout = new javax.swing.GroupLayout(kGradientPanel20);
        kGradientPanel20.setLayout(kGradientPanel20Layout);
        kGradientPanel20Layout.setHorizontalGroup(
            kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
                    .addGroup(kGradientPanel20Layout.createSequentialGroup()
                        .addGroup(kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel69)
                            .addComponent(jLabel97)
                            .addComponent(jLabel98)
                            .addComponent(jLabel100))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kGradientPanel20Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        kGradientPanel20Layout.setVerticalGroup(
            kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel20Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel98)
                .addGap(145, 145, 145)
                .addComponent(jLabel97)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel100)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kGradientPanel20Layout.createSequentialGroup()
                    .addGap(103, 103, 103)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(319, Short.MAX_VALUE)))
        );

        kGradientPanel21.setkBorderRadius(30);
        kGradientPanel21.setkEndColor(new java.awt.Color(255, 204, 102));
        kGradientPanel21.setkStartColor(new java.awt.Color(255, 204, 102));
        kGradientPanel21.setOpaque(false);

        kGradientPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        kGradientPanel22.setkBorderRadius(30);
        kGradientPanel22.setkEndColor(new java.awt.Color(255, 204, 102));
        kGradientPanel22.setkStartColor(new java.awt.Color(255, 204, 102));
        kGradientPanel22.setOpaque(false);

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(51, 51, 51));
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-information-75.png"))); // NOI18N
        jLabel62.setText("Information");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("RD : Mobil siap disewakan.");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("MT : Mobil dalam tahap perbaikan.");

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel79.setText("SW : Mobil sedang disewa.");

        javax.swing.GroupLayout kGradientPanel22Layout = new javax.swing.GroupLayout(kGradientPanel22);
        kGradientPanel22.setLayout(kGradientPanel22Layout);
        kGradientPanel22Layout.setHorizontalGroup(
            kGradientPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(kGradientPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel47)
                    .addComponent(jLabel79))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel22Layout.setVerticalGroup(
            kGradientPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel22Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(jLabel47)
                .addGap(18, 18, 18)
                .addComponent(jLabel79)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout kGradientPanel21Layout = new javax.swing.GroupLayout(kGradientPanel21);
        kGradientPanel21.setLayout(kGradientPanel21Layout);
        kGradientPanel21Layout.setHorizontalGroup(
            kGradientPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        kGradientPanel21Layout.setVerticalGroup(
            kGradientPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelLihatmobilLayout = new javax.swing.GroupLayout(panelLihatmobil);
        panelLihatmobil.setLayout(panelLihatmobilLayout);
        panelLihatmobilLayout.setHorizontalGroup(
            panelLihatmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLihatmobilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(kGradientPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLihatmobilLayout.setVerticalGroup(
            panelLihatmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLihatmobilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLihatmobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kGradientPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addComponent(kGradientPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelutama.add(panelLihatmobil, "card2");

        panelMT.setkBorderRadius(0);
        panelMT.setkEndColor(new java.awt.Color(137, 255, 253));
        panelMT.setkGradientFocus(2000);
        panelMT.setkStartColor(new java.awt.Color(97, 144, 232));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        jPanel2.setOpaque(false);

        yaMobil.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        yaMobil.setForeground(new java.awt.Color(51, 51, 51));
        yaMobil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/car_70px.png"))); // NOI18N
        yaMobil.setText("Your Car");

        outputPlmt.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outputPlmt.setForeground(new java.awt.Color(51, 51, 51));
        outputPlmt.setText("No Plat");
        outputPlmt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        outputMrkmt.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outputMrkmt.setForeground(new java.awt.Color(51, 51, 51));
        outputMrkmt.setText("Merk Mobil");
        outputMrkmt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel17.setText("Nomor Plat");

        jLabel26.setText("Merk Mobil");

        jLabel27.setText("Tindakan");

        jLabel30.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setText("untuk menyimpan");

        jLabel31.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("Silahkan klik tombol dibawah");

        sendMt.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        sendMt.setForeground(new java.awt.Color(51, 51, 51));
        sendMt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-next-page-60.png"))); // NOI18N
        sendMt.setText("Submit");
        sendMt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sendMt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendMtMouseClicked(evt);
            }
        });

        outputStnkmt.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outputStnkmt.setForeground(new java.awt.Color(51, 51, 51));
        outputStnkmt.setText("No STNK");
        outputStnkmt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel32.setText("Nomor STNK");

        bgTindakan.add(rbMt);
        rbMt.setText("Maintenance");
        rbMt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMtActionPerformed(evt);
            }
        });

        bgTindakan.add(rbMtFinished);
        rbMtFinished.setText("Assign");
        rbMtFinished.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMtFinishedActionPerformed(evt);
            }
        });

        bgTindakan.add(rbRetire);
        rbRetire.setText("Pensiun");
        rbRetire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRetireActionPerformed(evt);
            }
        });

        jLabel33.setText("Keterangan");

        inputKeterangan.setColumns(20);
        inputKeterangan.setRows(5);
        jScrollPane1.setViewportView(inputKeterangan);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(yaMobil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addComponent(sendMt))
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel27)
                                        .addComponent(outputPlmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel32)
                                        .addComponent(outputStnkmt, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rbMt)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbMtFinished)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbRetire))
                                    .addComponent(jLabel26)
                                    .addComponent(outputMrkmt, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yaMobil)
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(outputStnkmt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputPlmt))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputMrkmt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbMt)
                    .addComponent(rbMtFinished)
                    .addComponent(rbRetire))
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sendMt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel94.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(51, 51, 51));
        jLabel94.setText("Data Maintenance Mobil");

        tbmblMt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STNK", "No Plat", "Nama Mobil", "Merk Mobil", "Status"
            }
        ));
        tbmblMt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbmblMtMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbmblMt);

        jLabel95.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(51, 51, 51));
        jLabel95.setText("Data Mobil Dalam Perbaikan");

        jLabel96.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(51, 51, 51));
        jLabel96.setText("Data Mobil Ready");

        tbMblrd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STNK", "No Plat", "Nama Mobil", "Merk Mobil", "Status"
            }
        ));
        tbMblrd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMblrdMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbMblrd);
        if (tbMblrd.getColumnModel().getColumnCount() > 0) {
            tbMblrd.getColumnModel().getColumn(4).setHeaderValue("Status");
        }

        javax.swing.GroupLayout panelMTLayout = new javax.swing.GroupLayout(panelMT);
        panelMT.setLayout(panelMTLayout);
        panelMTLayout.setHorizontalGroup(
            panelMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(panelMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMTLayout.createSequentialGroup()
                            .addComponent(jLabel96)
                            .addGap(157, 157, 157))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMTLayout.createSequentialGroup()
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(panelMTLayout.createSequentialGroup()
                            .addComponent(jLabel95)
                            .addContainerGap()))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMTLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(panelMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMTLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jLabel94)
                    .addContainerGap(799, Short.MAX_VALUE)))
        );
        panelMTLayout.setVerticalGroup(
            panelMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMTLayout.createSequentialGroup()
                .addGroup(panelMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMTLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelMTLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel96)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel95)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMTLayout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jLabel94)
                    .addContainerGap(517, Short.MAX_VALUE)))
        );

        panelutama.add(panelMT, "card3");

        panelSewa.setkBorderRadius(0);
        panelSewa.setkEndColor(new java.awt.Color(137, 255, 253));
        panelSewa.setkGradientFocus(1000);
        panelSewa.setkStartColor(new java.awt.Color(97, 144, 232));
        panelSewa.setkTransparentControls(false);

        kGradientPanel12.setkBorderRadius(30);
        kGradientPanel12.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel12.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel12.setOpaque(false);

        kGradientPanel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        kGradientPanel23.setkBorderRadius(30);
        kGradientPanel23.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel23.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel23.setOpaque(false);

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(51, 51, 51));
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-information-75.png"))); // NOI18N
        jLabel74.setText("Information");

        btnbarcodescanner3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnbarcodescanner3.setForeground(new java.awt.Color(51, 51, 51));
        btnbarcodescanner3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fiat_500_80px.png"))); // NOI18N
        btnbarcodescanner3.setText("Sewa Mobil");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(153, 153, 153));
        jLabel43.setText("Menu untuk melayani penyewaan mobil");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(153, 153, 153));
        jLabel48.setText("Melakukan penyimpanan terhadap detil sewa");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(153, 153, 153));
        jLabel52.setText("untuk dihitung dan diproses oleh system");

        btninputmanual3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btninputmanual3.setForeground(new java.awt.Color(51, 51, 51));
        btninputmanual3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/key_80px.png"))); // NOI18N
        btninputmanual3.setText("Input Manual");

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(153, 153, 153));
        jLabel75.setText("Untuk mengembalikan kunci sewaan mobil");

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(153, 153, 153));
        jLabel76.setText("Menyimpan data kembalian mobil");

        javax.swing.GroupLayout kGradientPanel23Layout = new javax.swing.GroupLayout(kGradientPanel23);
        kGradientPanel23.setLayout(kGradientPanel23Layout);
        kGradientPanel23Layout.setHorizontalGroup(
            kGradientPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel23Layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(kGradientPanel23Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(kGradientPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(jLabel48)
                    .addComponent(jLabel52)
                    .addComponent(btnbarcodescanner3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75)
                    .addComponent(jLabel76)
                    .addComponent(btninputmanual3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel23Layout.setVerticalGroup(
            kGradientPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74)
                .addGap(18, 18, 18)
                .addComponent(btnbarcodescanner3)
                .addGap(29, 29, 29)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btninputmanual3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel75)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel76)
                .addGap(74, 74, 74))
        );

        javax.swing.GroupLayout kGradientPanel12Layout = new javax.swing.GroupLayout(kGradientPanel12);
        kGradientPanel12.setLayout(kGradientPanel12Layout);
        kGradientPanel12Layout.setHorizontalGroup(
            kGradientPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        kGradientPanel12Layout.setVerticalGroup(
            kGradientPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addContainerGap())
        );

        kGradientPanel24.setkBorderRadius(30);
        kGradientPanel24.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel24.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel24.setOpaque(false);

        btnSewambl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSewambl.setForeground(new java.awt.Color(51, 51, 51));
        btnSewambl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fiat_500_80px.png"))); // NOI18N
        btnSewambl.setText("Sewa Mobil");
        btnSewambl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSewambl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSewamblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel24Layout = new javax.swing.GroupLayout(kGradientPanel24);
        kGradientPanel24.setLayout(kGradientPanel24Layout);
        kGradientPanel24Layout.setHorizontalGroup(
            kGradientPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel24Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(btnSewambl, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        kGradientPanel24Layout.setVerticalGroup(
            kGradientPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel24Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnSewambl)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        kGradientPanel25.setkBorderRadius(30);
        kGradientPanel25.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel25.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel25.setOpaque(false);
        kGradientPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kGradientPanel25MouseClicked(evt);
            }
        });

        btnReturnmbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnReturnmbl.setForeground(new java.awt.Color(51, 51, 51));
        btnReturnmbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/key_80px.png"))); // NOI18N
        btnReturnmbl.setText("Return Mobil");
        btnReturnmbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturnmbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReturnmblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel25Layout = new javax.swing.GroupLayout(kGradientPanel25);
        kGradientPanel25.setLayout(kGradientPanel25Layout);
        kGradientPanel25Layout.setHorizontalGroup(
            kGradientPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel25Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(btnReturnmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        kGradientPanel25Layout.setVerticalGroup(
            kGradientPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel25Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnReturnmbl)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSewaLayout = new javax.swing.GroupLayout(panelSewa);
        panelSewa.setLayout(panelSewaLayout);
        panelSewaLayout.setHorizontalGroup(
            panelSewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSewaLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(panelSewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kGradientPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        panelSewaLayout.setVerticalGroup(
            panelSewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSewaLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(kGradientPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(kGradientPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSewaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        panelutama.add(panelSewa, "card2");

        panelUsewa.setkBorderRadius(0);
        panelUsewa.setkEndColor(new java.awt.Color(204, 102, 255));
        panelUsewa.setkGradientFocus(2000);
        panelUsewa.setkStartColor(new java.awt.Color(51, 255, 204));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        jPanel3.setOpaque(false);

        yaMobil1.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        yaMobil1.setForeground(new java.awt.Color(51, 51, 51));
        yaMobil1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/car_70px.png"))); // NOI18N
        yaMobil1.setText("Mobil Disewa");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel19.setText("Nama Penyewa");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel45.setText("Alamat Penyewa");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setText("Kontak Penyewa");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel54.setText("Nomor KTP Penyewa");

        txtnoKTP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtnoKTP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnoKTP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtnoKTP.setCaretColor(new java.awt.Color(204, 51, 255));
        txtnoKTP.setOpaque(false);

        txtNamasewa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNamasewa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNamasewa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtNamasewa.setCaretColor(new java.awt.Color(204, 51, 255));
        txtNamasewa.setOpaque(false);

        txtAlamatsewa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAlamatsewa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAlamatsewa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtAlamatsewa.setCaretColor(new java.awt.Color(204, 51, 255));
        txtAlamatsewa.setOpaque(false);

        txtBarangsewa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBarangsewa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBarangsewa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtBarangsewa.setCaretColor(new java.awt.Color(204, 51, 255));
        txtBarangsewa.setOpaque(false);

        yaMobil2.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        yaMobil2.setForeground(new java.awt.Color(51, 51, 51));
        yaMobil2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user64px.png"))); // NOI18N
        yaMobil2.setText("Biodata Penyewa");

        jLabel56.setText("Nomor Plat");

        outSewaplat.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outSewaplat.setForeground(new java.awt.Color(51, 51, 51));
        outSewaplat.setText("No Plat");
        outSewaplat.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel57.setText("Merk Mobil");

        outSewaMerk.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outSewaMerk.setForeground(new java.awt.Color(51, 51, 51));
        outSewaMerk.setText("Merk Mobil");
        outSewaMerk.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        outSewanama.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outSewanama.setForeground(new java.awt.Color(51, 51, 51));
        outSewanama.setText("Nama Mobil");
        outSewanama.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel58.setText("Nama Mobil");

        outSewaharga.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outSewaharga.setForeground(new java.awt.Color(51, 51, 51));
        outSewaharga.setText("Harga Sewa");
        outSewaharga.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel59.setText("Harga Sewa per Hari");

        outSewatotalharga.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outSewatotalharga.setForeground(new java.awt.Color(51, 51, 51));
        outSewatotalharga.setText("Harga Sewa");
        outSewatotalharga.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setText("Total Harga Sewa");

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel80.setText("Lama Sewa (Hari)");

        txtLamasewa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLamasewa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLamasewa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtLamasewa.setCaretColor(new java.awt.Color(204, 51, 255));
        txtLamasewa.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Rp.");

        btnSimpanpenyewa.setText("SIMPAN");
        btnSimpanpenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanpenyewaActionPerformed(evt);
            }
        });

        jLabel3.setText("NB : *Cek dengan menggunakan No KTP");

        outSewastnk.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outSewastnk.setForeground(new java.awt.Color(51, 51, 51));
        outSewastnk.setText("No STNK");
        outSewastnk.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));

        jLabel81.setText("No STNK");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel55.setText("Barang Ditinggalkan");

        txtKontaksewa1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKontaksewa1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtKontaksewa1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtKontaksewa1.setCaretColor(new java.awt.Color(204, 51, 255));
        txtKontaksewa1.setOpaque(false);

        btnCek.setText("CEK");
        btnCek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(yaMobil1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel80)
                                    .addComponent(txtLamasewa, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(95, 95, 95)
                                        .addComponent(jLabel78)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(outSewatotalharga, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnSimpanpenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCek, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(61, 61, 61)
                                .addComponent(txtBarangsewa, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAlamatsewa, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel45)
                    .addComponent(txtNamasewa, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnoKTP, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel54)
                    .addComponent(txtKontaksewa1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(outSewanama, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(outSewaplat, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57)
                    .addComponent(outSewaharga, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59)
                    .addComponent(outSewaMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outSewastnk, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81))
                .addGap(33, 33, 33))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(yaMobil2)
                    .addContainerGap(334, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(yaMobil1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(2, 2, 2)
                        .addComponent(txtnoKTP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamasewa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel45)
                        .addGap(10, 10, 10)
                        .addComponent(txtAlamatsewa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKontaksewa1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(outSewastnk))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel81)
                                .addGap(35, 35, 35)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(outSewanama))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addGap(35, 35, 35)))
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outSewaplat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(outSewaMerk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(outSewaharga)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpanpenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCek, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtBarangsewa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLamasewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(outSewatotalharga)
                            .addComponent(jLabel1))))
                .addGap(18, 18, 18))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(yaMobil2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(420, Short.MAX_VALUE)))
        );

        jLabel99.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(51, 51, 51));
        jLabel99.setText("Data Sewa Mobil");

        jLabel101.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(51, 51, 51));
        jLabel101.setText("Data Mobil Ready");

        tblmobilsewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No STNK", "No Plat", "Nama Mobil", "Merk Mobil", "Harga Sewa"
            }
        ));
        tblmobilsewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblmobilsewaMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblmobilsewa);

        jLabel53.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(51, 51, 51));
        jLabel53.setText("Dengan menekan tombol dibawah");

        jLabel50.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(51, 51, 51));
        jLabel50.setText("Penyewa telah setuju terhadap");

        jLabel77.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(51, 51, 51));
        jLabel77.setText("prasyarat perusahaan.");

        btnSewa.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        btnSewa.setForeground(new java.awt.Color(51, 51, 51));
        btnSewa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-next-page-60.png"))); // NOI18N
        btnSewa.setText("Submit");
        btnSewa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSewaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUsewaLayout = new javax.swing.GroupLayout(panelUsewa);
        panelUsewa.setLayout(panelUsewaLayout);
        panelUsewaLayout.setHorizontalGroup(
            panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsewaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99))
                .addGroup(panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsewaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelUsewaLayout.createSequentialGroup()
                                .addGroup(panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(btnSewa))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panelUsewaLayout.createSequentialGroup()
                        .addGroup(panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUsewaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel101))
                            .addGroup(panelUsewaLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelUsewaLayout.setVerticalGroup(
            panelUsewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsewaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel99)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelUsewaLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel101)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel77)
                .addGap(18, 18, 18)
                .addComponent(btnSewa)
                .addGap(42, 42, 42))
        );

        panelutama.add(panelUsewa, "card3");

        panelKembali.setkBorderRadius(0);
        panelKembali.setkEndColor(new java.awt.Color(137, 255, 253));
        panelKembali.setkGradientFocus(1000);
        panelKembali.setkStartColor(new java.awt.Color(97, 144, 232));
        panelKembali.setkTransparentControls(false);

        kGradientPanel26.setkBorderRadius(30);
        kGradientPanel26.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel26.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel26.setOpaque(false);

        jLabel82.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel82.setText("Data Mobil di Database");

        jLabel103.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(51, 51, 51));
        jLabel103.setText("Data Mobil Sedang Disewa");

        tblSewaactive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Trans", "Nama Mobil", "No STNK", "Plat No", "Nama Penyewa", "No KTP", "Nama Petugas", "TGL Sewa", "TGL Kembali"
            }
        ));
        tblSewaactive.setFocusable(false);
        tblSewaactive.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblSewaactive.setRowHeight(25);
        tblSewaactive.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tblSewaactive.setShowVerticalLines(false);
        tblSewaactive.getTableHeader().setReorderingAllowed(false);
        tblSewaactive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSewaactiveMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblSewaactive);

        submitReturn.setText("SUBMIT");
        submitReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitReturnActionPerformed(evt);
            }
        });

        jLabel91.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(51, 51, 51));
        jLabel91.setText("Mobil Terpilih : ");

        outReturnmbl.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outReturnmbl.setForeground(new java.awt.Color(51, 51, 51));
        outReturnmbl.setText("Mobil");

        jLabel92.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(51, 51, 51));
        jLabel92.setText("Id Transaksi :");

        outreturnIdtrans.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outreturnIdtrans.setForeground(new java.awt.Color(51, 51, 51));
        outreturnIdtrans.setText("Transaksi");

        outReturnnostnk.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        outReturnnostnk.setForeground(new java.awt.Color(51, 51, 51));
        outReturnnostnk.setText("No Stnk");

        jLabel102.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(51, 51, 51));
        jLabel102.setText("No Stnk :");

        javax.swing.GroupLayout kGradientPanel26Layout = new javax.swing.GroupLayout(kGradientPanel26);
        kGradientPanel26.setLayout(kGradientPanel26Layout);
        kGradientPanel26Layout.setHorizontalGroup(
            kGradientPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10)
            .addGroup(kGradientPanel26Layout.createSequentialGroup()
                .addGroup(kGradientPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(kGradientPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel82)
                            .addComponent(jLabel103)))
                    .addGroup(kGradientPanel26Layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(submitReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel91)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outReturnmbl))
                    .addGroup(kGradientPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel92)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outreturnIdtrans))
                    .addGroup(kGradientPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel102)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outReturnnostnk)))
                .addContainerGap(297, Short.MAX_VALUE))
        );
        kGradientPanel26Layout.setVerticalGroup(
            kGradientPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel26Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel82)
                .addGap(18, 18, 18)
                .addComponent(jLabel103)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(outReturnmbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(outreturnIdtrans))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(outReturnnostnk))
                .addGap(22, 22, 22)
                .addComponent(submitReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        kGradientPanel27.setkBorderRadius(30);
        kGradientPanel27.setkEndColor(new java.awt.Color(255, 204, 102));
        kGradientPanel27.setkStartColor(new java.awt.Color(255, 204, 102));
        kGradientPanel27.setOpaque(false);

        kGradientPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        kGradientPanel28.setkBorderRadius(30);
        kGradientPanel28.setkEndColor(new java.awt.Color(255, 204, 102));
        kGradientPanel28.setkStartColor(new java.awt.Color(255, 204, 102));
        kGradientPanel28.setOpaque(false);

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(51, 51, 51));
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-information-75.png"))); // NOI18N
        jLabel83.setText("Information");

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel84.setText("- Pilih Kolom hingga tabel focus");

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel85.setText("- Lalu tekan Submit");

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel86.setText("Menu ini digunakan untuk");

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel87.setText("Pengembalian kunci mobil.");

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel90.setText("Tata Cara :");

        javax.swing.GroupLayout kGradientPanel28Layout = new javax.swing.GroupLayout(kGradientPanel28);
        kGradientPanel28.setLayout(kGradientPanel28Layout);
        kGradientPanel28Layout.setHorizontalGroup(
            kGradientPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(kGradientPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel84)
                    .addComponent(jLabel85)
                    .addComponent(jLabel86)
                    .addComponent(jLabel87)
                    .addComponent(jLabel90))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel28Layout.setVerticalGroup(
            kGradientPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel28Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel83)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel84)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel85)
                .addGap(18, 18, 18)
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel87)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout kGradientPanel27Layout = new javax.swing.GroupLayout(kGradientPanel27);
        kGradientPanel27.setLayout(kGradientPanel27Layout);
        kGradientPanel27Layout.setHorizontalGroup(
            kGradientPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        kGradientPanel27Layout.setVerticalGroup(
            kGradientPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelKembaliLayout = new javax.swing.GroupLayout(panelKembali);
        panelKembali.setLayout(panelKembaliLayout);
        panelKembaliLayout.setHorizontalGroup(
            panelKembaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKembaliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelKembaliLayout.setVerticalGroup(
            panelKembaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKembaliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelKembaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kGradientPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addComponent(kGradientPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelutama.add(panelKembali, "card2");

        LaporanBulanan.setkBorderRadius(0);
        LaporanBulanan.setkEndColor(new java.awt.Color(137, 255, 253));
        LaporanBulanan.setkGradientFocus(1000);
        LaporanBulanan.setkStartColor(new java.awt.Color(97, 144, 232));
        LaporanBulanan.setkTransparentControls(false);

        tampungmonthlyreport.setkBorderRadius(60);
        tampungmonthlyreport.setkEndColor(new java.awt.Color(255, 255, 255));
        tampungmonthlyreport.setkStartColor(new java.awt.Color(255, 255, 255));
        tampungmonthlyreport.setOpaque(false);
        tampungmonthlyreport.setLayout(new java.awt.CardLayout());

        tampilanmonthlyreport.setkBorderRadius(60);
        tampilanmonthlyreport.setkEndColor(new java.awt.Color(255, 255, 255));
        tampilanmonthlyreport.setkStartColor(new java.awt.Color(255, 255, 255));
        tampilanmonthlyreport.setOpaque(false);

        tabelsheetmonthly.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelsheetmonthly.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "ID Transaksi", "Nama Mobil", "Nama Petugas", "Nama Penyewa", "Lama Sewa", "Biaya Sewa"
            }
        ));
        tabelsheetmonthly.setFocusable(false);
        tabelsheetmonthly.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelsheetmonthly.setRowHeight(25);
        tabelsheetmonthly.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tabelsheetmonthly.setShowVerticalLines(false);
        tabelsheetmonthly.getTableHeader().setReorderingAllowed(false);
        tabelsheetmonthly.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsheetmonthlyMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(tabelsheetmonthly);

        jLabel150.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel150.setText("Sheet Laporan Bulanan");

        bulandantahun.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        bulandantahun.setText("Bulan + Tahun");

        jLabel152.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel152.setText("Ringkasan Persewaan");

        jLabel153.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel153.setText("Pendapatan Sewa :");

        omsettokobulanan.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        omsettokobulanan.setText("0");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Rp.");

        btnPrint.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/print_65px.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrintMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout tampilanmonthlyreportLayout = new javax.swing.GroupLayout(tampilanmonthlyreport);
        tampilanmonthlyreport.setLayout(tampilanmonthlyreportLayout);
        tampilanmonthlyreportLayout.setHorizontalGroup(
            tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tampilanmonthlyreportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20)
                    .addGroup(tampilanmonthlyreportLayout.createSequentialGroup()
                        .addGroup(tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tampilanmonthlyreportLayout.createSequentialGroup()
                                .addComponent(jLabel150)
                                .addGap(18, 18, 18)
                                .addComponent(bulandantahun))
                            .addGroup(tampilanmonthlyreportLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel153)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(omsettokobulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(tampilanmonthlyreportLayout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(jLabel152)
                .addContainerGap(334, Short.MAX_VALUE))
        );
        tampilanmonthlyreportLayout.setVerticalGroup(
            tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tampilanmonthlyreportLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel150)
                    .addComponent(bulandantahun))
                .addGap(23, 23, 23)
                .addComponent(jLabel152)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tampilanmonthlyreportLayout.createSequentialGroup()
                        .addGroup(tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel153)
                            .addGroup(tampilanmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(omsettokobulanan)))
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tampungmonthlyreport.add(tampilanmonthlyreport, "card2");

        emptymonthlyreport.setkBorderRadius(60);
        emptymonthlyreport.setkEndColor(new java.awt.Color(255, 255, 255));
        emptymonthlyreport.setkStartColor(new java.awt.Color(255, 255, 255));
        emptymonthlyreport.setOpaque(false);

        jLabel186.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        jLabel186.setText("Dan Tekan Refresh Untuk Menampilkan Data");

        jLabel187.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        jLabel187.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-information-75.png"))); // NOI18N
        jLabel187.setText(" Silakan Pilih Laporan Bulanan Yang Akan Ditampilkan");

        javax.swing.GroupLayout emptymonthlyreportLayout = new javax.swing.GroupLayout(emptymonthlyreport);
        emptymonthlyreport.setLayout(emptymonthlyreportLayout);
        emptymonthlyreportLayout.setHorizontalGroup(
            emptymonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptymonthlyreportLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(emptymonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel187, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(emptymonthlyreportLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel186)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        emptymonthlyreportLayout.setVerticalGroup(
            emptymonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptymonthlyreportLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel187)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel186)
                .addContainerGap(254, Short.MAX_VALUE))
        );

        tampungmonthlyreport.add(emptymonthlyreport, "card2");

        subpanelpilihmonthlyreport.setkBorderRadius(40);
        subpanelpilihmonthlyreport.setkEndColor(new java.awt.Color(255, 255, 255));
        subpanelpilihmonthlyreport.setkStartColor(new java.awt.Color(255, 255, 255));
        subpanelpilihmonthlyreport.setOpaque(false);

        pilihbulan.setkEndColor(new java.awt.Color(255, 255, 255));
        pilihbulan.setkStartColor(new java.awt.Color(255, 255, 255));

        jLabel104.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel104.setText("Pilih Bulan Penjualan");

        ubulan2.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        ubulan2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "Maret ", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        utahun2.setBackground(new java.awt.Color(0, 204, 204));
        utahun2.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        utahun2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));

        jLabel160.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel160.setText("Monthly Report ");

        jLabel161.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel161.setText("Sewa Mobil ");

        javax.swing.GroupLayout pilihbulanLayout = new javax.swing.GroupLayout(pilihbulan);
        pilihbulan.setLayout(pilihbulanLayout);
        pilihbulanLayout.setHorizontalGroup(
            pilihbulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pilihbulanLayout.createSequentialGroup()
                .addGroup(pilihbulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel104)
                    .addComponent(jLabel160)
                    .addComponent(jLabel161)
                    .addGroup(pilihbulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(utahun2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ubulan2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        pilihbulanLayout.setVerticalGroup(
            pilihbulanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pilihbulanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel160)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel161)
                .addGap(18, 18, 18)
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ubulan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(utahun2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        refreshsheetmonthly.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        refreshsheetmonthly.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-refresh-65.png"))); // NOI18N
        refreshsheetmonthly.setText("Refresh");
        refreshsheetmonthly.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshsheetmonthly.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshsheetmonthlyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshsheetmonthlyMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout subpanelpilihmonthlyreportLayout = new javax.swing.GroupLayout(subpanelpilihmonthlyreport);
        subpanelpilihmonthlyreport.setLayout(subpanelpilihmonthlyreportLayout);
        subpanelpilihmonthlyreportLayout.setHorizontalGroup(
            subpanelpilihmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subpanelpilihmonthlyreportLayout.createSequentialGroup()
                .addGroup(subpanelpilihmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subpanelpilihmonthlyreportLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pilihbulan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subpanelpilihmonthlyreportLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(refreshsheetmonthly)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        subpanelpilihmonthlyreportLayout.setVerticalGroup(
            subpanelpilihmonthlyreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subpanelpilihmonthlyreportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pilihbulan, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addComponent(refreshsheetmonthly, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout LaporanBulananLayout = new javax.swing.GroupLayout(LaporanBulanan);
        LaporanBulanan.setLayout(LaporanBulananLayout);
        LaporanBulananLayout.setHorizontalGroup(
            LaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LaporanBulananLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(subpanelpilihmonthlyreport, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(859, 859, 859))
            .addGroup(LaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LaporanBulananLayout.createSequentialGroup()
                    .addContainerGap(223, Short.MAX_VALUE)
                    .addComponent(tampungmonthlyreport, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(25, Short.MAX_VALUE)))
        );
        LaporanBulananLayout.setVerticalGroup(
            LaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanBulananLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(subpanelpilihmonthlyreport, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(LaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LaporanBulananLayout.createSequentialGroup()
                    .addContainerGap(51, Short.MAX_VALUE)
                    .addComponent(tampungmonthlyreport, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelutama.add(LaporanBulanan, "card2");

        getContentPane().add(panelutama, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 63, 1040, 578));

        panelstatus.setBackground(new java.awt.Color(255, 153, 153));
        panelstatus.setLayout(new java.awt.CardLayout());

        statusutama.setkBorderRadius(0);
        statusutama.setkEndColor(new java.awt.Color(239, 50, 217));
        statusutama.setkGradientFocus(1000);
        statusutama.setkStartColor(new java.awt.Color(137, 255, 253));
        statusutama.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                statusutamaMouseDragged(evt);
            }
        });
        statusutama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                statusutamaMousePressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sistem Informasi Persewaan Mobil");

        tombolminim1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolminim1.setForeground(new java.awt.Color(255, 255, 255));
        tombolminim1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Starting/iconminimize.png"))); // NOI18N
        tombolminim1.setText("\n");
        tombolminim1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolminim1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolminim1MouseClicked(evt);
            }
        });

        tombolclose1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolclose1.setForeground(new java.awt.Color(255, 255, 255));
        tombolclose1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconclose50.png"))); // NOI18N
        tombolclose1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolclose1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolclose1MouseClicked(evt);
            }
        });

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconcashier1_64px.png"))); // NOI18N

        timenow.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        timenow.setText("Waktu Saat Ini");

        labelcp.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        labelcp.setForeground(new java.awt.Color(255, 255, 255));
        labelcp.setText("Copyright @ Kelompok Saya 2019 D3 SI");
        labelcp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        labelcp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelcpMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout statusutamaLayout = new javax.swing.GroupLayout(statusutama);
        statusutama.setLayout(statusutamaLayout);
        statusutamaLayout.setHorizontalGroup(
            statusutamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusutamaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timenow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(statusutamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(labelcp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolminim1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolclose1))
        );
        statusutamaLayout.setVerticalGroup(
            statusutamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusutamaLayout.createSequentialGroup()
                .addGroup(statusutamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tombolclose1)
                    .addComponent(jLabel25)
                    .addGroup(statusutamaLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(timenow, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(statusutamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, statusutamaLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelcp))
                        .addComponent(tombolminim1, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelstatus.add(statusutama, "card2");

        statusutamacashier.setkBorderRadius(0);
        statusutamacashier.setkEndColor(new java.awt.Color(239, 50, 217));
        statusutamacashier.setkGradientFocus(1000);
        statusutamacashier.setkStartColor(new java.awt.Color(137, 255, 253));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Sistem Informasi Kasir Koperasi Mahasiswa");

        tombolminim.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolminim.setForeground(new java.awt.Color(255, 255, 255));
        tombolminim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Starting/iconminimize.png"))); // NOI18N
        tombolminim.setText("\n");
        tombolminim.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolminim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolminimMouseClicked(evt);
            }
        });

        tombolclose.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolclose.setForeground(new java.awt.Color(255, 255, 255));
        tombolclose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconclose50.png"))); // NOI18N
        tombolclose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolcloseMouseClicked(evt);
            }
        });

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconcashier1_64px.png"))); // NOI18N

        timenow1.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        timenow1.setText("Waktu Saat Ini");

        javax.swing.GroupLayout statusutamacashierLayout = new javax.swing.GroupLayout(statusutamacashier);
        statusutamacashier.setLayout(statusutamacashierLayout);
        statusutamacashierLayout.setHorizontalGroup(
            statusutamacashierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusutamacashierLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timenow1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolminim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolclose))
        );
        statusutamacashierLayout.setVerticalGroup(
            statusutamacashierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusutamacashierLayout.createSequentialGroup()
                .addGroup(statusutamacashierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusutamacashierLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20))
                    .addComponent(tombolminim)
                    .addComponent(tombolclose)
                    .addComponent(jLabel70)
                    .addGroup(statusutamacashierLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(timenow1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelstatus.add(statusutamacashier, "card2");

        statuspanelpresensi.setkBorderRadius(0);
        statuspanelpresensi.setkEndColor(new java.awt.Color(239, 50, 217));
        statuspanelpresensi.setkGradientFocus(1000);
        statuspanelpresensi.setkStartColor(new java.awt.Color(137, 255, 253));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sistem Informasi Kasir Koperasi Mahasiswa");

        tombolminim4.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolminim4.setForeground(new java.awt.Color(255, 255, 255));
        tombolminim4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Starting/iconminimize.png"))); // NOI18N
        tombolminim4.setText("\n");
        tombolminim4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolminim4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolminim4MouseClicked(evt);
            }
        });

        tombolclose4.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolclose4.setForeground(new java.awt.Color(255, 255, 255));
        tombolclose4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconclose50.png"))); // NOI18N
        tombolclose4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolclose4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolclose4MouseClicked(evt);
            }
        });

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconcashier1_64px.png"))); // NOI18N

        timenow2.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        timenow2.setText("Waktu Saat Ini");

        javax.swing.GroupLayout statuspanelpresensiLayout = new javax.swing.GroupLayout(statuspanelpresensi);
        statuspanelpresensi.setLayout(statuspanelpresensiLayout);
        statuspanelpresensiLayout.setHorizontalGroup(
            statuspanelpresensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statuspanelpresensiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timenow2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolminim4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolclose4))
        );
        statuspanelpresensiLayout.setVerticalGroup(
            statuspanelpresensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statuspanelpresensiLayout.createSequentialGroup()
                .addGroup(statuspanelpresensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statuspanelpresensiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11))
                    .addComponent(tombolminim4)
                    .addComponent(tombolclose4)
                    .addComponent(jLabel44)
                    .addGroup(statuspanelpresensiLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(timenow2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelstatus.add(statuspanelpresensi, "card2");

        statuspanelfeedback.setkBorderRadius(0);
        statuspanelfeedback.setkEndColor(new java.awt.Color(239, 50, 217));
        statuspanelfeedback.setkGradientFocus(1000);
        statuspanelfeedback.setkStartColor(new java.awt.Color(137, 255, 253));

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setText("Sistem Informasi Persewaan Mobil");

        tombolminim2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolminim2.setForeground(new java.awt.Color(255, 255, 255));
        tombolminim2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Starting/iconminimize.png"))); // NOI18N
        tombolminim2.setText("\n");
        tombolminim2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolminim2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolminim2MouseClicked(evt);
            }
        });

        tombolclose2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        tombolclose2.setForeground(new java.awt.Color(255, 255, 255));
        tombolclose2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconclose50.png"))); // NOI18N
        tombolclose2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolclose2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombolclose2MouseClicked(evt);
            }
        });

        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iconcashier1_64px.png"))); // NOI18N

        timenow3.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        timenow3.setText("Waktu Saat Ini");

        javax.swing.GroupLayout statuspanelfeedbackLayout = new javax.swing.GroupLayout(statuspanelfeedback);
        statuspanelfeedback.setLayout(statuspanelfeedbackLayout);
        statuspanelfeedbackLayout.setHorizontalGroup(
            statuspanelfeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statuspanelfeedbackLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timenow3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel89)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolminim2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolclose2))
        );
        statuspanelfeedbackLayout.setVerticalGroup(
            statuspanelfeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statuspanelfeedbackLayout.createSequentialGroup()
                .addGroup(statuspanelfeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statuspanelfeedbackLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel88))
                    .addComponent(tombolminim2)
                    .addComponent(tombolclose2)
                    .addComponent(jLabel89)
                    .addGroup(statuspanelfeedbackLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(timenow3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelstatus.add(statuspanelfeedback, "card2");

        getContentPane().add(panelstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 1040, 61));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void tombolminim1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolminim1MouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_tombolminim1MouseClicked

    private void tombolclose1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolclose1MouseClicked
        // TODO add your handling code here:
        this.dispose();
        System.exit(0);
//        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_tombolclose1MouseClicked
  
    public void setStatusresensi(String verif) {

    }


    private void tombolminimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolminimMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_tombolminimMouseClicked

    private void tombolcloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolcloseMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_tombolcloseMouseClicked

    private void tombolminim4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolminim4MouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_tombolminim4MouseClicked

    private void tombolclose4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolclose4MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_tombolclose4MouseClicked


    String stattabel = "Muncul";
    private void tombolminim2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolminim2MouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_tombolminim2MouseClicked

    private void tombolclose2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolclose2MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_tombolclose2MouseClicked


    private void tombollogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombollogoutMouseClicked
        // TODO add your handling code here:
        this.dispose();
        LoginForm lg = new LoginForm();
        lg.pack();
        lg.setLocationRelativeTo(null);
        lg.show();
    }//GEN-LAST:event_tombollogoutMouseClicked

    private void tombollogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombollogoutMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tombollogoutMouseEntered

    private void labelcpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelcpMouseClicked

    }//GEN-LAST:event_labelcpMouseClicked

        private void statusutamaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statusutamaMouseDragged
                // TODO add your handling code here:
		new Drag(statusutama).moveWindow(evt);
        }//GEN-LAST:event_statusutamaMouseDragged

        private void statusutamaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statusutamaMousePressed
                // TODO add your handling code here:
		new Drag(statusutama).onPress(evt);
        }//GEN-LAST:event_statusutamaMousePressed

        private void tombolpresensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolpresensiMouseClicked
            String waktu = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date());
            String waktudate = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
            String waktujam = new SimpleDateFormat("hh:mm:ss").format(new java.util.Date());
            String NoKTP = outputNoktp.getText();
            String idabsen = NoKTP+waktudate;
            PreparedStatement ps;
            
            try {
                sql = "insert into persons(ID_ABSEN, NOKTP, ABSEN_JAM, ABSEN_TANGGAL) values('"+idabsen+"','"+NoKTP+"','"+waktudate+"','"+waktujam+"')";
                ps = koneksidb.getConnection().prepareStatement(sql);
                if (ps.executeUpdate() > 0) {
                    statusabsen = "sudah";
                    dialog_absenberhasil sudahabse = new dialog_absenberhasil(nama);
                    sudahabse.setVisible(true);
                    sudahabse.pack();
                    sudahabse.setLocationRelativeTo(null);
                    subpanelstatusabsen.removeAll();
                    subpanelstatusabsen.repaint();
                    subpanelstatusabsen.revalidate();
                    subpanelstatusabsen.add(statusverified);
                    subpanelstatusabsen.repaint();
                    subpanelstatusabsen.revalidate();
                }
            } catch (Exception ex) {
                System.out.println("gagal writing ke db");
                System.out.println(ex);
                punten_absen ok = new punten_absen();
                ok.setVisible(true);
                ok.pack();
                ok.setLocationRelativeTo(null);
                ok.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            
        }//GEN-LAST:event_tombolpresensiMouseClicked

        private void tampilkanabsenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tampilkanabsenMouseClicked
                // TODO add your handling code here:
                tampilabsen();

        }//GEN-LAST:event_tampilkanabsenMouseClicked

    private void tombollihatabsensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombollihatabsensiMouseClicked
        // TODO add your handling code here:
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();

        panelutama.add(laporanAbsen);
        panelutama.repaint();
        panelutama.revalidate();
    }//GEN-LAST:event_tombollihatabsensiMouseClicked

    private void tombollihatabsensiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombollihatabsensiMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tombollihatabsensiMouseEntered

    private void tombolMobilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolMobilMouseClicked
        // TODO add your handling code here:
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();

        panelstatus.removeAll();
        panelstatus.repaint();
        panelstatus.revalidate();

        panelutama.add(panelMobil);
        panelutama.repaint();
        panelutama.revalidate();

        panelstatus.add(statusutama);
        panelstatus.repaint();
        panelstatus.revalidate();
    }//GEN-LAST:event_tombolMobilMouseClicked

    private void tombolMtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolMtMouseClicked
        // TODO add your handling code here:
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();
        
        panelutama.add(panelMT);
        panelutama.repaint();
        panelutama.revalidate();
        
        tampilMobilrd();
        tampilMobilmt();
        
        inputKeterangan.setEnabled(false);

    }//GEN-LAST:event_tombolMtMouseClicked

    private void tombollihatbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombollihatbarangMouseClicked
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();
        
        panelutama.add(panelLihatmobil);
        panelutama.repaint();
        panelutama.revalidate();
        
        tampilVmblrd();
        tampilVmblrt();
        tampilVmblsewa();
    }//GEN-LAST:event_tombollihatbarangMouseClicked

    private void kGradientPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_kGradientPanel19MouseClicked

    private void tomboleditbarang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tomboleditbarang1MouseClicked
        // TODO add your handling code here:
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();
        
        panelutama.add(panelEditmobil);
        panelutama.repaint();
        panelutama.revalidate();
        
        tampilMobil();
    }//GEN-LAST:event_tomboleditbarang1MouseClicked

    private void tombolkembalibigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolkembalibigMouseClicked
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();

        panelutama.add(panelLanding);
        panelutama.repaint();
        panelutama.revalidate();
    }//GEN-LAST:event_tombolkembalibigMouseClicked

    private void btnbtninputkedatabaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbtninputkedatabaseMouseClicked
        String query2 = "INSERT INTO `mobil`(`NOSTNK`, `PLAT_NO`, `JENIS_MOBIL`, `NAMA_MOBIL`, `MERK_MOBIL`, `HARGA_SEWA`, `STATUS`) VALUES (?,?,?,?,?,?,?)";

        try {
            ps = koneksidb.getConnection().prepareStatement(query2);
            ps.setString(1, inputNostnk.getText());
            ps.setString(2, inputPlat.getText());
            ps.setString(3, cbJenismbl.getSelectedItem().toString());
            ps.setString(4, inputNamambl.getText());
            ps.setString(5, inputmerkmbl.getText());
            ps.setString(6, inputHrgsewambl.getText());
            ps.setString(7, "RD");

            if (ps.executeUpdate() > 0) {
                System.out.println("Behasil Diinput");
                dialog_writingdatabarangberhasil ok = new dialog_writingdatabarangberhasil(nama);
                ok.setVisible(true);
                ok.pack();
                ok.setLocationRelativeTo(null);
                ok.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tampilMobil();
            }

        } catch (SQLException ex) {
            dialog_writingdatabaranggagal ok = new dialog_writingdatabaranggagal(nama);
            ok.setVisible(true);
            ok.pack();
            ok.setLocationRelativeTo(null);
            ok.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.out.println(ex);
//            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnbtninputkedatabaseMouseClicked

    private void btndeleteitemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteitemMouseClicked
        // TODO add your handling code here: 
        DefaultTableModel x = (DefaultTableModel) tabelMobil.getModel();
        int sel = tabelMobil.getSelectedRow();
        String unique = String.valueOf(x.getValueAt(sel, 0));
        String querydelete = "DELETE FROM mobil WHERE NOSTNK = '" + unique + "' ";
        
        String status = cekstatus(unique);
        
        if(status.equalsIgnoreCase("RD")){
            PreparedStatement ps;
            try {
                ps = koneksidb.getConnection().prepareStatement(querydelete);
                ps.executeUpdate(querydelete);
                //This closes the connection to the database
                ps.close();
                tampilMobil();
                System.out.println("Behasil Hapus Barang Dari DataBase");
            } catch (Exception aa) {
                System.out.println("Gagal Hapus Dari Database");
                System.out.println(aa);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mobil Sedang Tidak Tersedai!");
        }
    }//GEN-LAST:event_btndeleteitemMouseClicked

    private String cekstatus(String nostnk){
        String status = "";
        try{
            String querycek = "SELECT * FROM mobil WHERE NOSTNK = "+nostnk;
            PreparedStatement ps = koneksidb.getConnection().prepareStatement(querycek);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = rs.getString(7);
            }
        } catch (Exception ea) {
            System.out.println(ea);
        }
        
        return status;
    }
    
    private void btngetselectedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngetselectedMouseClicked
         try {
            String nostnk, plat_no, jenismobil, namamobil, merkmobil, hargasewa;
            PreparedStatement ps;
            DefaultTableModel x = (DefaultTableModel) tabelMobil.getModel();
            int sel = tabelMobil.getSelectedRow();
            nostnk = String.valueOf(x.getValueAt(sel, 0));
            plat_no = String.valueOf(x.getValueAt(sel, 1));
            jenismobil = String.valueOf(x.getValueAt(sel, 2));
            namamobil = String.valueOf(x.getValueAt(sel, 3));
            merkmobil = String.valueOf(x.getValueAt(sel, 4));
            hargasewa = String.valueOf(x.getValueAt(sel, 5));

            inputNostnk.setText(nostnk);
            inputPlat.setText(plat_no);
            cbJenismbl.setSelectedItem(jenismobil);
            inputNamambl.setText(namamobil);
            inputmerkmbl.setText(merkmobil);
            inputHrgsewambl.setText(hargasewa);

        } catch (Exception gagalselect) {
            System.out.println("Gagal Select Database");
            System.out.println(gagalselect);
        }
    }//GEN-LAST:event_btngetselectedMouseClicked

    private void btnedititemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnedititemMouseClicked
        DefaultTableModel x = (DefaultTableModel) tabelMobil.getModel();
        int sel = tabelMobil.getSelectedRow();
        String nostnk, pmbl, jmbl, nmbl, merkmbl, hsmbl;
        nostnk = String.valueOf(x.getValueAt(sel, 0));
        pmbl = inputPlat.getText();
        jmbl = (String) cbJenismbl.getSelectedItem();
        nmbl = inputNamambl.getText();
        merkmbl = inputmerkmbl.getText();
        hsmbl = inputHrgsewambl.getText();

        PreparedStatement ps;
        String queryupdate = "UPDATE `mobil` SET `NOSTNK`=?,`PLAT_NO`=?,`JENIS_MOBIL`=?,`NAMA_MOBIL`=?,`MERK_MOBIL`=?,`HARGA_SEWA`=? WHERE NOSTNK=" + "'" + nostnk + "'";
        try {
            ps = koneksidb.getConnection().prepareStatement(queryupdate);
            ps.setString(1, inputNostnk.getText());
            ps.setString(2, pmbl);
            ps.setString(3, jmbl);
            ps.setString(4, nmbl);
            ps.setString(5, merkmbl);
            ps.setString(6, hsmbl);
            ps.executeUpdate();
            System.out.println("Berhasil Diupdate");
            tampilMobil();
            if (ps.executeUpdate() > 0) {
                System.out.println("Behasil Diedit");
                System.out.println("Berhasil Update/Edit DATABASE");
            }

        } catch (SQLException ex) {
            System.out.println("Gagal Update/Edit DATABASE");
            System.out.println(ex);

        }
    }//GEN-LAST:event_btnedititemMouseClicked

    private void tombolSewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolSewaMouseClicked
       // TODO add your handling code here:
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();

        panelutama.add(panelSewa);
        panelutama.repaint();
        panelutama.revalidate();
    }//GEN-LAST:event_tombolSewaMouseClicked

    private void updateMt(String stnk, String Status){
        PreparedStatement ps;
        String queryupdate = "UPDATE `mobil` SET `STATUS` = ? WHERE NOSTNK=" + "'" + stnk + "'";
        try {
            ps = koneksidb.getConnection().prepareStatement(queryupdate);
            ps.setString(1, Status);
            ps.executeUpdate();
            System.out.println("Berhasil Diupdate");
            tampilMobil();
            if (ps.executeUpdate() > 0) {
                System.out.println("Behasil Diedit");
                System.out.println("Berhasil Update/Edit DATABASE");
            }

        } catch (SQLException ex) {
            System.out.println("Gagal Update/Edit DATABASE");
            System.out.println(ex);
        }
    }
    
    private void sendMtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendMtMouseClicked
        String waktudate = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        String NoKTP = sessionKtp;
        String NoSTNK = outputStnkmt.getText();
        String idCek = NoKTP+NoSTNK;
        String descCek = inputKeterangan.getText();
        
        if(rbMt.isSelected()) {
            String query2 = "INSERT INTO `cek_mobil`(`ID_PENGECEKAN`, `NOSTNK`, `NOKTP`, `DESKRIPSI_PENGECEKAN`, `TANGGAL_PENGECEKAN`) VALUES (?,?,?,?,?)";

        try {
            ps = koneksidb.getConnection().prepareStatement(query2);
            ps.setString(1, idCek);
            ps.setString(2, NoSTNK);
            ps.setString(3, NoKTP);
            ps.setString(4, descCek);
            ps.setString(5, waktudate);

            if (ps.executeUpdate() > 0) {
                System.out.println("Behasil Diinput");
                dialog_writingdatabarangberhasil ok = new dialog_writingdatabarangberhasil(nama);
                ok.setVisible(true);
                ok.pack();
                ok.setLocationRelativeTo(null);
                ok.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                updateMt(NoSTNK, "MT");
            }

        } catch (SQLException ex) {
            dialog_writingdatabaranggagal ok = new dialog_writingdatabaranggagal(nama);
            ok.setVisible(true);
            ok.pack();
            ok.setLocationRelativeTo(null);
            ok.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.out.println(ex);
//            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        } else if (rbMtFinished.isSelected()) {
            updateMt(NoSTNK, "RD");
        } else if (rbRetire.isSelected()) {
            updateMt(NoSTNK, "RT");
        }
        tampilMobilmt();
        tampilMobilrd();
        inputKeterangan.setText("");
    }//GEN-LAST:event_sendMtMouseClicked

    private void tbMblrdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMblrdMouseClicked
        inputKeterangan.setText("");
        inputKeterangan.setText("");
        int selIndex = tbMblrd.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tbMblrd.getModel();
        yaMobil.setText(tbMblrd.getModel().getValueAt(selIndex, 2).toString());
        outputStnkmt.setText(tbMblrd.getModel().getValueAt(selIndex, 0).toString());
        outputPlmt.setText(tbMblrd.getModel().getValueAt(selIndex, 1).toString());
        outputMrkmt.setText(tbMblrd.getModel().getValueAt(selIndex, 3).toString());
        inputKeterangan.setEnabled(false);
        rbMt.setSelected(false);
        rbMtFinished.setSelected(false);
        rbRetire.setSelected(false);
    }//GEN-LAST:event_tbMblrdMouseClicked

    private void tbmblMtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbmblMtMouseClicked
        // TODO add your handling code here:
        int selIndex = tbmblMt.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tbmblMt.getModel();
        if (tbmblMt.getModel().getValueAt(selIndex, 4).toString().equalsIgnoreCase("MT")){
            yaMobil.setText(tbmblMt.getModel().getValueAt(selIndex, 2).toString());
            outputStnkmt.setText(tbmblMt.getModel().getValueAt(selIndex, 0).toString());
            outputPlmt.setText(tbmblMt.getModel().getValueAt(selIndex, 1).toString());
            outputMrkmt.setText(tbmblMt.getModel().getValueAt(selIndex, 3).toString());
            rbMt.setSelected(true);
            inputKeterangan.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Mobil Telah Pensiun, Hubungi Admin untuk menambahkan!", "Warning", 2);
        }

    }//GEN-LAST:event_tbmblMtMouseClicked

    private void rbMtFinishedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMtFinishedActionPerformed
        // TODO add your handling code here:
        inputKeterangan.setEnabled(false);
    }//GEN-LAST:event_rbMtFinishedActionPerformed

    private void rbRetireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRetireActionPerformed
        // TODO add your handling code here:
        inputKeterangan.setEnabled(false);
    }//GEN-LAST:event_rbRetireActionPerformed

    private void rbMtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMtActionPerformed
        // TODO add your handling code here:
        inputKeterangan.setEnabled(true);
    }//GEN-LAST:event_rbMtActionPerformed

    private void tombolMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolMobilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tombolMobilActionPerformed

    private void btnSewamblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSewamblMouseClicked
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();

        panelutama.add(panelUsewa);
        panelutama.repaint();
        panelutama.revalidate();
        
        tampilMobilrdsewa();
    }//GEN-LAST:event_btnSewamblMouseClicked

    private void btnReturnmblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnmblMouseClicked
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();

        panelutama.add(panelKembali);
        panelutama.repaint();
        panelutama.revalidate();
        
        tampilMblreturn();
    }//GEN-LAST:event_btnReturnmblMouseClicked

    private void kGradientPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel25MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_kGradientPanel25MouseClicked

    private void tombolLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolLaporanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tombolLaporanMouseClicked

    private void tombolLaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombolLaporanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tombolLaporanMouseEntered

    private void btnSewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSewaMouseClicked
        // TODO add your handling code here:
        if (txtnoKTP.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "No KTP Tidak Boleh Kosong");
        }
        if (txtNamasewa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama Tidak Boleh Kosong");
        }
        if (txtAlamatsewa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Username Tidak Boleh Kosong");
        }
        if (txtKontaksewa1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Telp Tidak Boleh Kosong");
        }
        if (txtBarangsewa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Barang yang ditinggalkan Tidak Boleh Kosong");
        }
        else {
            int dialogBtn = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this,"Apakah data telah benar ?", "PERINGATAN", dialogBtn);

            String status = cekSewa(txtnoKTP.getText());
            System.out.println("Status = " + status);
            if (status.equals("non")) {
                if (dialogResult == 0) {
                    //Jika Benar
                    String waktudate = new SimpleDateFormat("YYYY-MM-DD").format(new java.util.Date());
                    String notransaksi = waktudate + txtnoKTP.getText();
                    String biayasewa = outSewatotalharga.getName();
                    System.out.println(biayasewa);
                    //String query2 = "INSERT INTO `detil_sewa`(`ID_TRANSAKSI`, `NOSTNK`, `NOKTP_PENYEWA`, `NOKTP`, `TANGGAL_SEWA`, `TANGGAL_KEMBALI`, `BIAYA_SEWA`, `BARANG_PENYEWA`, `STATUS_SEWA`,) VALUES (?,?,?,?,?,?,?,?,?)";
                    String sql = "INSERT INTO `detil_sewa`(`ID_TRANSAKSI`, `NOSTNK`, `NOKTP_PENYEWA`, `NOKTP`, `TANGGAL_SEWA`, `BIAYA_SEWA`, `BARANG_PENYEWA`, `STATUS_SEWA`) VALUES ('"+notransaksi+"', '"+outSewastnk.getText()+"', '"
                            +txtnoKTP.getText()+"', '"+sessionKtp+"', "+"CURDATE()"+", "+"'"+outSewatotalharga.getText()+"', '"+txtBarangsewa.getText()+"', '"+"SW')";
                    try {
                        ps = koneksidb.getConnection().prepareStatement(sql);
                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showConfirmDialog(null,"Data berhasil di input!", "SUCCESS!", JOptionPane.DEFAULT_OPTION);
                        tampilMobilrdsewa();
                        updateMt(outSewastnk.getText(), "SW");
                        txtnoKTP.setText("");
                        txtNamasewa.setText("");
                        txtAlamatsewa.setText("");
                        txtKontaksewa1.setText("");
                    }

                    } catch (SQLException ex) {
                        dialog_writingdatabaranggagal ok = new dialog_writingdatabaranggagal(nama);
                        ok.setVisible(true);
                        ok.pack();
                        ok.setLocationRelativeTo(null);
                        ok.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        System.out.println(ex);
            //            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {}
            } else if (status.equals("sewa")) {
                JOptionPane.showMessageDialog(null, "Penyewa masih memiliki tanggungan mobil !", "Warning", 2);
            }
        }
        
    }//GEN-LAST:event_btnSewaMouseClicked

    private String cekSewa(String noktpSewa) {
    String status = "non";
        try{
            String querycek = "SELECT * FROM detil_sewa WHERE NOKTP_PENYEWA = "+noktpSewa+" AND STATUS_SEWA = 'SW'";
            PreparedStatement ps = koneksidb.getConnection().prepareStatement(querycek);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = "sewa";
            }
        } catch (Exception ea) {
            System.out.println(ea);
        }
        
        return status;
    }
    
    private void tblmobilsewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblmobilsewaMouseClicked
        // TODO add your handling code here:
        try{
        int selIndex = tblmobilsewa.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tblmobilsewa.getModel();
        outSewastnk.setText(tblmobilsewa.getModel().getValueAt(selIndex, 0).toString());
        outSewanama.setText(tblmobilsewa.getModel().getValueAt(selIndex, 2).toString());
        outSewaplat.setText(tblmobilsewa.getModel().getValueAt(selIndex, 1).toString());
        outSewaMerk.setText(tblmobilsewa.getModel().getValueAt(selIndex, 3).toString());
        outSewaharga.setText(tblmobilsewa.getModel().getValueAt(selIndex, 4).toString());
        double totalSewa = Double.valueOf(tblmobilsewa.getModel().getValueAt(selIndex, 4).toString()) * Integer.parseInt(txtLamasewa.getText());
        outSewatotalharga.setText(String.valueOf(totalSewa));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Harap Cek Kembali Biodata/Lama Sewa", "Warning", 2);
            System.out.println(e);
        }
    }//GEN-LAST:event_tblmobilsewaMouseClicked

    private void btnSimpanpenyewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanpenyewaActionPerformed
        String status = "";
        status = cekUser(txtnoKTP.getText());
        
        if(status.equals("terdaftar"))
        {
            JOptionPane.showConfirmDialog(null,"User telah terdaftar ! Silahkan masukkan lama sewa lalu pilih mobil", "SUCCESS!", JOptionPane.DEFAULT_OPTION);
            try{
            String querycek = "SELECT * FROM penyewa WHERE noktp_penyewa = "+txtnoKTP.getText();
            PreparedStatement ps = koneksidb.getConnection().prepareStatement(querycek);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                txtnoKTP.setText(rs.getString(1));
                txtNamasewa.setText(rs.getString(2));
                txtAlamatsewa.setText(rs.getString(3));
                txtKontaksewa1.setText(rs.getString(4));
            }
        } catch (Exception ea) {
            System.out.println(ea);
        }
        }
        else if (status.equals("non")) {
            
            if (txtnoKTP.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "No KTP Tidak Boleh Kosong");
            }
            if (txtNamasewa.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Nama Tidak Boleh Kosong");
            }
            if (txtAlamatsewa.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Username Tidak Boleh Kosong");
            }
            if (txtKontaksewa1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Telp Tidak Boleh Kosong");
            }
            else {
                String query2 = "INSERT INTO `penyewa`(`NOKTP_PENYEWA`, `NAMA_PENYEWA`, `ALAMAT_PENYEWA`, `KONTAK_PENYEWA`) VALUES (?,?,?,?)";

                try {
                    ps = koneksidb.getConnection().prepareStatement(query2);
                    ps.setString(1, txtnoKTP.getText());
                    ps.setString(2, txtNamasewa.getText());
                    ps.setString(3, txtAlamatsewa.getText());
                    ps.setString(4, txtBarangsewa.getText());

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Behasil Diinput");
                        JOptionPane.showConfirmDialog(null,"User telah didaftarkan silahkan memilih mobil !", "SUCCESS!", JOptionPane.DEFAULT_OPTION);
                        tampilMobil();
                    }

                } catch (SQLException ex) {
                    dialog_writingdatabaranggagal ok = new dialog_writingdatabaranggagal(nama);
                    ok.setVisible(true);
                    ok.pack();
                    ok.setLocationRelativeTo(null);
                    ok.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.out.println(ex);
        //            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
        }
    }//GEN-LAST:event_btnSimpanpenyewaActionPerformed

    private void tblSewaactiveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSewaactiveMouseClicked
        int selIndex = tblSewaactive.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tblSewaactive.getModel();
        outreturnIdtrans.setText(tblSewaactive.getModel().getValueAt(selIndex, 0).toString());
        outReturnnostnk.setText(tblSewaactive.getModel().getValueAt(selIndex, 2).toString());
        outReturnmbl.setText(tblSewaactive.getModel().getValueAt(selIndex, 1).toString());
    }//GEN-LAST:event_tblSewaactiveMouseClicked

    
    private void submitReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitReturnActionPerformed
            int dialogBtn = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this,"Konfirmasi Pengembalian.", "PERINGATAN", dialogBtn);
                if (dialogResult == 0) {
                    //Jika Benar
                    updateDtmobil(outreturnIdtrans.getText());
                    updateMt(outReturnnostnk.getText(), "RD");
                    JOptionPane.showConfirmDialog(null,"Teriimakasih atas kerjasama anda !", "SUCCESS!", JOptionPane.DEFAULT_OPTION);
                    tampilMblreturn();
                    outreturnIdtrans.setText("");
                    outReturnnostnk.setText("");
                    outReturnmbl.setText("");
                } else {}
        
    }//GEN-LAST:event_submitReturnActionPerformed

    private void tabelsheetmonthlyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsheetmonthlyMouseClicked

    }//GEN-LAST:event_tabelsheetmonthlyMouseClicked

    private void refreshsheetmonthlyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshsheetmonthlyMouseClicked
        analisislaporanbulanan();

    }//GEN-LAST:event_refreshsheetmonthlyMouseClicked

    private void analisislaporanbulanan(){
        String b = String.valueOf(ubulan2.getSelectedItem());
        String c = String.valueOf(utahun2.getSelectedItem());
        String bulan = null;
        if (b.equals("January")) {
            bulan = "01";
        }
        if (b.equals("February")) {
            bulan = "02";
        }
        if (b.equals("Maret")) {
            bulan = "03";
        }
        if (b.equals("April")) {
            bulan = "04";
        }
        if (b.equals("Mei")) {
            bulan = "05";
        }
        if (b.equals("Juni")) {
            bulan = "06";
        }
        if (b.equals("July")) {
            bulan = "07";
        }
        if (b.equals("Agustus")) {
            bulan = "08";
        }
        if (b.equals("September")) {
            bulan = "09";
        }
        if (b.equals("Oktober")) {
            bulan = "10";
        }
        if (b.equals("November")) {
            bulan = "11";
        }
        if (b.equals("Desember")) {
            bulan = "12";
        }
        
        String sql = c+"-"+bulan+"%";
        tampilLaporan(sql);
        int total = 0;
        
        for (int i = 0; i < tabelsheetmonthly.getRowCount(); i++) {
            int Amount = Integer.parseInt(tabelsheetmonthly.getValueAt(i, 6) + "");
            total = Amount + total;
            omsettokobulanan.setText(String.valueOf(total));
        }
        
        tampungmonthlyreport.removeAll();
        tampungmonthlyreport.repaint();
        tampungmonthlyreport.revalidate();

        tampungmonthlyreport.add(tampilanmonthlyreport);
        tampungmonthlyreport.repaint();
        tampungmonthlyreport.revalidate();
        
    }
    
    private void refreshsheetmonthlyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshsheetmonthlyMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshsheetmonthlyMouseEntered

    private void btnCekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekActionPerformed
        try{

            String querycek = "SELECT * FROM penyewa WHERE noktp_penyewa = "+txtnoKTP.getText();
            PreparedStatement ps = koneksidb.getConnection().prepareStatement(querycek);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                txtnoKTP.setText(rs.getString(1));
                txtNamasewa.setText(rs.getString(2));
                txtAlamatsewa.setText(rs.getString(3));
                txtKontaksewa1.setText(rs.getString(4));
                count++;
            } 
            if (count == 1) {
                JOptionPane.showConfirmDialog(null,"User telah terdaftar ! Silahkan masukkan lama sewa lalu pilih mobil", "SUCCESS!", JOptionPane.DEFAULT_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "User dengan no ktp tersebut belum terdaftar.", "Warning", 2);
            }
        } catch (Exception ea) {
            System.out.println(ea);
        }
    }//GEN-LAST:event_btnCekActionPerformed

    private void tombolLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolLaporanActionPerformed
        panelutama.removeAll();
        panelutama.repaint();
        panelutama.revalidate();
        
        panelutama.add(LaporanBulanan);
        panelutama.repaint();
        panelutama.revalidate();
    }//GEN-LAST:event_tombolLaporanActionPerformed

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        try {
            // TODO add your handling code here:
            if (tabelsheetmonthly.getRowCount()>0){
                exportDataToExcel();
            } 
            else {
                JOptionPane.showMessageDialog(null, "Data tidak ada.", "Warning", 2);
            }
        } catch (IOException ex) {
            Logger.getLogger(landingmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPrintMouseClicked

    private void btnPrintMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintMouseEntered

    public void exportDataToExcel() throws FileNotFoundException, IOException {
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\Authentic\\Desktop");
        //Dialog box title
        excelFileChooser.setDialogTitle("Save As ..");
        //Filter only xls, xlsx, xlsm files
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("xls", "xlsx");
        //Setting extension for selected file names
        excelFileChooser.setFileFilter(fnef);
        int chooser = excelFileChooser.showSaveDialog(null);
        
        
        Workbook wb = new HSSFWorkbook();
        CreationHelper createhelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");
        Row row = null;
        Cell cell = null;
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("No.");
        cell = row.createCell(1);
        cell.setCellValue("ID Transaksi");
        cell = row.createCell(2);
        cell.setCellValue("Nama Mobil");
        cell = row.createCell(3);
        cell.setCellValue("Nama Petugas");
        cell = row.createCell(4);
        cell.setCellValue("Nama Penyewa");
        cell = row.createCell(5);
        cell.setCellValue("Lama Sewa");
        cell = row.createCell(6);
        cell.setCellValue("Biaya Sewa");
        row = sheet.createRow(2);
        int i =0;
        for (i=3;i<tabelsheetmonthly.getRowCount()+3;i++) {
            row = sheet.createRow(i);
            for (int j=0;j<tabelsheetmonthly.getColumnCount();j++) {
                cell = row.createCell(j);
                cell.setCellValue((String) tabelsheetmonthly.getValueAt(i-3, j));
            }
        }
        row = sheet.createRow(i+1);
        cell = row.createCell(1);
        cell.setCellValue("Pendapatan Sewa : ");
        cell = row.createCell(2);
        cell.setCellValue("Rp. "+omsettokobulanan.getText());
        
        FileOutputStream out = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xls");
        wb.write(out);
        out.close();
        JOptionPane.showMessageDialog(null, "Exported Successfully");
    } 
    
    private void updateDtmobil(String idTrans) {
        PreparedStatement ps;
        String queryupdate = "UPDATE `DETIL_SEWA` SET `STATUS_SEWA` = 'BK', `TANGGAL_KEMBALI` = CURDATE() WHERE ID_TRANSAKSI=" + "'" + idTrans + "'";
        System.out.println(queryupdate);
        try {
            ps = koneksidb.getConnection().prepareStatement(queryupdate);
            ps.executeUpdate();
            System.out.println("Berhasil Diupdate");
            tampilMobil();
            if (ps.executeUpdate() > 0) {
                
                System.out.println("Behasil Diedit");
                System.out.println("Berhasil Update/Edit DATABASE");
            }

        } catch (SQLException ex) {
            System.out.println("Gagal Update/Edit DATABASE");
            System.out.println(ex);
        }
    }
    
    
    private String cekUser(String noktpSewa) {
    String status = "non";
        try{
            String querycek = "SELECT * FROM penyewa WHERE noktp_penyewa = "+noktpSewa;
            PreparedStatement ps = koneksidb.getConnection().prepareStatement(querycek);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = "terdaftar";
            }
        } catch (Exception ea) {
            System.out.println(ea);
        }
        
        return status;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(landingmenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(landingmenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(landingmenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(landingmenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KGradientPanel LaporanBulanan;
    private javax.swing.ButtonGroup bgTindakan;
    private keeptoo.KButton btnCek;
    private javax.swing.JLabel btnPrint;
    private javax.swing.JLabel btnReturnmbl;
    private javax.swing.JLabel btnSewa;
    private javax.swing.JLabel btnSewambl;
    private keeptoo.KButton btnSimpanpenyewa;
    private javax.swing.JLabel btnbarcodescanner2;
    private javax.swing.JLabel btnbarcodescanner3;
    private javax.swing.JLabel btndeleteitem;
    private javax.swing.JLabel btnedititem;
    private javax.swing.JLabel btngetselected;
    private javax.swing.JLabel btninputkedatabase;
    private javax.swing.JLabel btninputmanual2;
    private javax.swing.JLabel btninputmanual3;
    private javax.swing.JLabel bulandantahun;
    private javax.swing.JComboBox<String> cbJenismbl;
    private keeptoo.KGradientPanel emptymonthlyreport;
    private javax.swing.JTextField inputHrgsewambl;
    private javax.swing.JTextArea inputKeterangan;
    private javax.swing.JTextField inputNamambl;
    private javax.swing.JTextField inputNostnk;
    private javax.swing.JTextField inputPlat;
    private javax.swing.JTextField inputmerkmbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private keeptoo.KGradientPanel kGradientPanel11;
    private keeptoo.KGradientPanel kGradientPanel12;
    private keeptoo.KGradientPanel kGradientPanel14;
    private keeptoo.KGradientPanel kGradientPanel15;
    private keeptoo.KGradientPanel kGradientPanel16;
    private keeptoo.KGradientPanel kGradientPanel17;
    private keeptoo.KGradientPanel kGradientPanel18;
    private keeptoo.KGradientPanel kGradientPanel19;
    private keeptoo.KGradientPanel kGradientPanel20;
    private keeptoo.KGradientPanel kGradientPanel21;
    private keeptoo.KGradientPanel kGradientPanel22;
    private keeptoo.KGradientPanel kGradientPanel23;
    private keeptoo.KGradientPanel kGradientPanel24;
    private keeptoo.KGradientPanel kGradientPanel25;
    private keeptoo.KGradientPanel kGradientPanel26;
    private keeptoo.KGradientPanel kGradientPanel27;
    private keeptoo.KGradientPanel kGradientPanel28;
    private keeptoo.KGradientPanel kGradientPanel38;
    private javax.swing.JLabel labelcp;
    private keeptoo.KGradientPanel laporanAbsen;
    private javax.swing.JLabel omsettokobulanan;
    private javax.swing.JLabel outReturnmbl;
    private javax.swing.JLabel outReturnnostnk;
    private javax.swing.JLabel outSewaMerk;
    private javax.swing.JLabel outSewaharga;
    private javax.swing.JLabel outSewanama;
    private javax.swing.JLabel outSewaplat;
    private javax.swing.JLabel outSewastnk;
    private javax.swing.JLabel outSewatotalharga;
    private javax.swing.JLabel outputAlamat;
    private javax.swing.JLabel outputMrkmt;
    private javax.swing.JLabel outputNama;
    private javax.swing.JLabel outputNoktp;
    private javax.swing.JLabel outputPlmt;
    private javax.swing.JLabel outputStnkmt;
    private javax.swing.JLabel outputkontak;
    private javax.swing.JLabel outreturnIdtrans;
    private keeptoo.KGradientPanel panelEditmobil;
    private keeptoo.KGradientPanel panelKembali;
    private keeptoo.KGradientPanel panelLanding;
    private keeptoo.KGradientPanel panelLihatmobil;
    private keeptoo.KGradientPanel panelMT;
    private keeptoo.KGradientPanel panelMobil;
    private keeptoo.KGradientPanel panelSewa;
    private keeptoo.KGradientPanel panelUsewa;
    private javax.swing.JPanel panelmenu;
    private javax.swing.JPanel panelstatus;
    private javax.swing.JPanel panelutama;
    private keeptoo.KGradientPanel pilihbulan;
    private javax.swing.JRadioButton rbMt;
    private javax.swing.JRadioButton rbMtFinished;
    private javax.swing.JRadioButton rbRetire;
    private javax.swing.JLabel refreshsheetmonthly;
    private javax.swing.JLabel sendMt;
    private keeptoo.KGradientPanel statuspanelfeedback;
    private keeptoo.KGradientPanel statuspanelpresensi;
    private keeptoo.KGradientPanel statusunverified;
    private keeptoo.KGradientPanel statusutama;
    private keeptoo.KGradientPanel statusutamacashier;
    private keeptoo.KGradientPanel statusverified;
    private keeptoo.KButton submitReturn;
    private keeptoo.KGradientPanel subpanelpilihmonthlyreport;
    private keeptoo.KGradientPanel subpanelstatusabsen;
    private javax.swing.JTable tabelMobil;
    private javax.swing.JTable tabelpresensi;
    private javax.swing.JTable tabelsheetmonthly;
    private keeptoo.KGradientPanel tampilanmonthlyreport;
    private javax.swing.JLabel tampilkanabsen;
    private keeptoo.KGradientPanel tampungmonthlyreport;
    private javax.swing.JTable tbMblrd;
    private javax.swing.JTable tbViewmblrd;
    private javax.swing.JTable tbVmblrt;
    private javax.swing.JTable tblSewaactive;
    private javax.swing.JTable tblmobilsewa;
    private javax.swing.JTable tbmblMt;
    private javax.swing.JTable tbvMblsewa;
    private javax.swing.JLabel timenow;
    private javax.swing.JLabel timenow1;
    private javax.swing.JLabel timenow2;
    private javax.swing.JLabel timenow3;
    private keeptoo.KButton tombolLaporan;
    private keeptoo.KButton tombolMobil;
    private javax.swing.JLabel tombolMt;
    private keeptoo.KButton tombolSewa;
    private javax.swing.JLabel tombolclose;
    private javax.swing.JLabel tombolclose1;
    private javax.swing.JLabel tombolclose2;
    private javax.swing.JLabel tombolclose4;
    private javax.swing.JLabel tomboleditbarang1;
    private javax.swing.JLabel tombolkembalibig;
    private keeptoo.KButton tombollihatabsensi;
    private javax.swing.JLabel tombollihatbarang;
    private keeptoo.KButton tombollogout;
    private javax.swing.JLabel tombolminim;
    private javax.swing.JLabel tombolminim1;
    private javax.swing.JLabel tombolminim2;
    private javax.swing.JLabel tombolminim4;
    private javax.swing.JLabel tombolpresensi;
    private javax.swing.JTextField txtAlamatsewa;
    private javax.swing.JTextField txtBarangsewa;
    private javax.swing.JTextField txtKontaksewa1;
    private javax.swing.JTextField txtLamasewa;
    private javax.swing.JTextField txtNamasewa;
    private javax.swing.JTextField txtnoKTP;
    private javax.swing.JComboBox<String> ubulan2;
    private javax.swing.JComboBox<String> utahun2;
    private javax.swing.JLabel yaMobil;
    private javax.swing.JLabel yaMobil1;
    private javax.swing.JLabel yaMobil2;
    private javax.swing.JLabel yaccount;
    // End of variables declaration//GEN-END:variables
}
