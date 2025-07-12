/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package alumni_202457201064;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author siti novi triana
 */
public class PanelKelas extends javax.swing.JPanel {
    DefaultTableModel model;
    int rowSelected =  -1;

    /**
     * Creates new form PanelKelas
     */
    public PanelKelas() {
        initComponents();
        reset();
        load_tabel_jurusan();
        combojurusan();
        comboWali();
}
        void reset(){
        //kosongkan isi text field kode jurusan
        tKodeKelas.setText(null);
        // aktifkan kembali text field kode jurusan agar bisa diedit 
        tKodeKelas.setEditable(true);
        //kosongkan isi text field nama jurusan
        tNamaKelas.setText(null);
        
        cbTingkatan.setSelectedItem(null);
        cbJurusan.setSelectedItem(null);
        cbWaliKelas.setSelectedItem(null);
    }
        void load_tabel_jurusan(){
        //membuat objek model tabel baru
        DefaultTableModel model = new DefaultTableModel();
        //menambahkan kolom kedalam model tabel
        //kolom pertama untuk kode jurusan
        model.addColumn("Kode Kelas");
        //kolom kedua untuk nama jurusan
        model.addColumn("Nama Kelas");
        model.addColumn("Tingkatan");
        model.addColumn("Jurusan");
        model.addColumn("Wali Kelas");
        
        //query SQL untuk mengambil semua data dari table jurusan
        String sql = "SELECT k.id_kelas,k.nama_kelas,k.tingkatan,j.nama_jurusan,g.nama_guru "
                + "FROM kelas k "
                + "LEFT JOIN jurusan j ON k.kode_jur=j.kode_jur "
                + "LEFT JOIN guru g ON k.nip_wali_kelas=g.nip";
        
            try {
                //membuka koneksi kedatabase
                Connection conn = koneksi.konek();

                //membuka statement untuk menjalankan array
                Statement st = conn.createStatement();

                //menjalankan query dan menyimpan hasilnya dalam ResultSet
                ResultSet rs = st.executeQuery(sql);

                //melakukan iterasi untuk setiap baris data hasil query
                while (rs.next()) {
                    //mengambil data kolom kode_jur
                    String kodeKelas = rs.getString("id_kelas");

                    //mengambil data kolom nama_jurusan
                    String namaKelas = rs.getString("nama_kelas");
                    
                    //mengambil data kolom nama_jurusan
                    String tingkatan = rs.getString("tingkatan");
                    
                    //mengambil data kolom nama_jurusan
                    String jurusan = rs.getString("nama_jurusan");
                    
                    //mengambil data kolom nama_jurusan
                    String waliKelas = rs.getString("nama_guru");

                    //membuat array berisi data satu baris
                    Object[] baris = {kodeKelas, namaKelas, tingkatan, jurusan, waliKelas};

                    //menambahkan array baris kedalam model table
                    model.addRow(baris);

                }
            } catch (SQLException sQLException) {
            //menampilkan pesan error jika gagal mengambil data dari database
            JOptionPane.showMessageDialog(null, "Gagal Mengambil Data!");
            }

            //menampilkan model yang sudah diisi kedalam tabel GUI
            TableKelas.setModel(model);
        }
        
        void  combojurusan(){
            //query untuk mengambil semua data dari tabel jurusan
            String sql = "SELECT * FROM jurusan";
            
             try {
                //membuka koneksi kedatabase
                Connection conn = koneksi.konek();

                //membuka statement untuk menjalankan array
                Statement statement = conn.createStatement();

                //menjalankan query dan menyimpan hasilnya dalam ResultSet
                ResultSet resultSet = statement.executeQuery(sql);

                //tambahkan setiap nama jurusan kedalam combo box
                while (resultSet.next()) {
                    cbJurusan.addItem(resultSet.getString("nama_jurusan"));
                }
            } catch (SQLException sQLException) {
            }
             cbJurusan.setSelectedItem(null);
        }
    
        void comboWali(){
            String sql = "SELECT * FROM guru";
            try {
                //membuka koneksi kedatabase
                Connection conn = koneksi.konek();

                //membuka statement untuk menjalankan array
                Statement statement = conn.createStatement();

                //menjalankan query dan menyimpan hasilnya dalam ResultSet
                ResultSet resultSet = statement.executeQuery(sql);

                //tambahkan setiap nama jurusan kedalam combo box
                while (resultSet.next()) {
                    cbWaliKelas.addItem(resultSet.getString("nama_guru"));
                }
            } catch (SQLException sQLException) {
            }
            cbWaliKelas.setSelectedItem(null);
        }
        
        String KodeJurusan(String NamaJurusan){
             String sql = "SELECT * FROM jurusan WHERE nama_jurusan = ?";
             
             try {
                //query SQL untuk menyisipkan data ketable jurusan
                Connection conn = koneksi.konek();

                //siapkan query SQL untuk dieksekusi dengan parameter
                PreparedStatement ps = conn.prepareStatement(sql);

                //isi  parameter pertama (?) dengan kode Jurusan
                ps.setString(1, NamaJurusan);

                //menjalankan query dan menyimpan hasilnya dalam ResultSet
                ResultSet resultSet = ps.executeQuery(sql);
                while (resultSet.next()) {
                    return resultSet.getString("kode_jur");
                }
            } catch (SQLException sQLException) {
                //jika error, kembalikan string kosong
                return "";
            }
            return "";
        }

        String NIP(String NamaGuru){
            String sql = "SELECT * FROM guru WHERE nama_guru = ?";
             
             try {
                //query SQL untuk menyisipkan data ketable jurusan
                Connection conn = koneksi.konek();

                //siapkan query SQL untuk dieksekusi dengan parameter
                PreparedStatement ps = conn.prepareStatement(sql);

                //isi  parameter pertama (?) dengan kode Jurusan
                ps.setString(1, NamaGuru);

                //menjalankan query dan menyimpan hasilnya dalam ResultSet
                ResultSet resultSet = ps.executeQuery(sql);
                while (resultSet.next()) {
                  return resultSet.getString("nip");   
                }
            } catch (SQLException sQLException) {
                //jika error, kembalikan string kosong
                return "";
            }
             return "";
        }
        
       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelIsi = new javax.swing.JPanel();
        bTambah = new javax.swing.JButton();
        bUbah = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableKelas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        cbJurusan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbTingkatan = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        tNamaKelas = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tKodeKelas = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bTutup = new javax.swing.JButton();
        cbWaliKelas = new javax.swing.JComboBox<>();

        bTambah.setBackground(new java.awt.Color(0, 204, 51));
        bTambah.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        bTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/alumni_202457201064/gambar/icons8-plus-20.png"))); // NOI18N
        bTambah.setText("Tambah");
        bTambah.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bUbah.setBackground(new java.awt.Color(255, 153, 51));
        bUbah.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        bUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/alumni_202457201064/gambar/icons8-note-20.png"))); // NOI18N
        bUbah.setText("Ubah");
        bUbah.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahActionPerformed(evt);
            }
        });

        bHapus.setBackground(new java.awt.Color(255, 51, 51));
        bHapus.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        bHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/alumni_202457201064/gambar/icons8-delete-20.png"))); // NOI18N
        bHapus.setText("Hapus");
        bHapus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bReset.setBackground(new java.awt.Color(0, 102, 255));
        bReset.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        bReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/alumni_202457201064/gambar/icons8-reset-20 (1).png"))); // NOI18N
        bReset.setText("Reset");
        bReset.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });

        TableKelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Kelas", "Nama Kelas", "Tingkatan", "Jurusan", "Wali Kelas"
            }
        ));
        TableKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableKelasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableKelas);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setText("Wali Kelas");

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setText("Jurusan");

        cbTingkatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10", "11", "12" }));
        cbTingkatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTingkatanActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel6.setText("Tingkatan");

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setText("Nama Kelas");

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel2.setText("Kode Kelas");

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Kelas");

        bTutup.setBackground(new java.awt.Color(102, 255, 102));
        bTutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/alumni_202457201064/gambar/icons8-close-20.png"))); // NOI18N
        bTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTutupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 946, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bTutup, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(bTutup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelIsiLayout = new javax.swing.GroupLayout(PanelIsi);
        PanelIsi.setLayout(PanelIsiLayout);
        PanelIsiLayout.setHorizontalGroup(
            PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIsiLayout.createSequentialGroup()
                .addGroup(PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1025, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelIsiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelIsiLayout.createSequentialGroup()
                .addGroup(PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIsiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tKodeKelas, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbJurusan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTingkatan, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tNamaKelas)
                            .addGroup(PanelIsiLayout.createSequentialGroup()
                                .addGroup(PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbWaliKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelIsiLayout.createSequentialGroup()
                        .addGap(334, 334, 334)
                        .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(bUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(bHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(bReset, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(71, 71, 71))
        );
        PanelIsiLayout.setVerticalGroup(
            PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelIsiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIsiLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tKodeKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tNamaKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTingkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbWaliKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelIsiLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(PanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTambah)
                    .addComponent(bUbah)
                    .addComponent(bHapus)
                    .addComponent(bReset))
                .addContainerGap(415, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelIsi, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(PanelIsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTutupActionPerformed
        // TODO add your handling code here:
        PanelIsi.setVisible(false);

    }//GEN-LAST:event_bTutupActionPerformed

    private void cbTingkatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTingkatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTingkatanActionPerformed

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_bResetActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        //ambil input dari text field tKodeJurusan1 dan disimpan ke variable kodeJurusan
            String kodeJurusan = tKodeKelas.getText();
            //query SQL untuk mengubah data pada table 
            String sql = "DELETE FROM kelas WHERE kode_jur=?";
            
            try {
            //query SQL untuk menyisipkan data ketable jurusan
            Connection conn = koneksi.konek();

            //siapkan query SQL untuk dieksekusi dengan parameter
            PreparedStatement ps = conn.prepareStatement(sql);

            //isi  parameter pertama (?) dengan kode Jurusan
            ps.setString(1, kodeJurusan);
            
            //jalankan query untuk menyimpan data ke databse
            ps.execute();
            //tampilkan pesan bahwa data berhasil disimpan
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
            
        } catch (SQLException sQLException) {
        //jika terjadi kesalahan saat menyimpan data, tampilkan pesan gagal
        JOptionPane.showMessageDialog(null, "Data Gagal Disimpan!");
                System.out.println(sQLException);
        }
            //panggil method untuk memuat ulang data pada table jurusan
           load_tabel_jurusan();
           //panggil method untuk mereset atau mengosongkan inputan
           reset();
    }//GEN-LAST:event_bHapusActionPerformed

    private void bUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahActionPerformed
        // TODO add your handling code here:
            String KodeKelas = tKodeKelas.getText().trim();
            String NamaKelas = tNamaKelas.getText().trim();
            String tingkatan = cbTingkatan.getSelectedItem().toString().trim();
            String jurusan = cbJurusan.getSelectedItem().toString().trim();
            String WaliKelas = cbWaliKelas.getSelectedItem().toString().trim();
            
            try {
            String sql = "INSERT INTO kelas(id_kelas,nama_kelas,tingkatan,kode_jur,nip_wali_kelas) VALUES (?,?,?,?,?)";
            
            Connection conn = koneksi.konek();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, NamaKelas);
            //isi  parameter pertama (?) dengan nama Jurusan
            statement.setString(2, tingkatan);
            statement.setString(3, jurusan);
            statement.setString(4, WaliKelas);
            statement.setString(5, KodeKelas);
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan!");
                System.out.println(sQLException);
        }
        //panggil method untuk memuat ulang data pada table jurusan
           load_tabel_jurusan();
           //panggil method untuk mereset atau mengosongkan inputan
           reset();

    }//GEN-LAST:event_bUbahActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        // TODO add your handling code here:
            String KodeKelas = tKodeKelas.getText().trim();
            String NamaKelas = tNamaKelas.getText().trim();
            String tingkatan = cbTingkatan.getSelectedItem().toString();
            String jurusan = cbJurusan.getSelectedItem().toString();
            String WaliKelas = cbWaliKelas.getSelectedItem().toString();
            
            //query SQL untuk mengubah data pada table 
            String sql = "INSERT INTO kelas(id_kelas,nama_kelas,tingkatan,kode_jur,nip_wali_kelas) VALUES (?,?,?,?,?)";
            
           try {
            //query SQL untuk menyisipkan data ketable jurusan
            Connection conn = koneksi.konek();

            //siapkan query SQL untuk dieksekusi dengan parameter
            PreparedStatement statement = conn.prepareStatement(sql);

            //isi  parameter pertama (?) dengan kode Jurusan
            statement.setString(1, KodeKelas);
            //isi  parameter pertama (?) dengan nama Jurusan
            statement.setString(2, NamaKelas);
            statement.setString(3, tingkatan);
            statement.setString(4, jurusan);
            statement.setString(5, WaliKelas);

            //jalankan query untuk menyimpan data ke databse
            statement.execute();
            //tampilkan pesan bahwa data berhasil disimpan
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
            
         } catch (SQLException sQLException) {
        //jika terjadi kesalahan saat menyimpan data, tampilkan pesan gagal
             JOptionPane.showMessageDialog(null, "Data Gagal Disimpan!");
               System.out.println(sQLException);
        }
            //panggil method untuk memuat ulang data pada table jurusan
           load_tabel_jurusan();
           //panggil method untuk mereset atau mengosongkan inputan
           reset();
   
    }//GEN-LAST:event_bTambahActionPerformed

    private void TableKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableKelasMouseClicked
        // TODO add your handling code here:
        int barisYangDipilih = TableKelas.rowAtPoint(evt.getPoint());

// Ambil nilai dari setiap kolom sesuai urutan yang benar
            String kodeKelas = TableKelas.getValueAt(barisYangDipilih, 0).toString();
            String namaKelas = TableKelas.getValueAt(barisYangDipilih, 1).toString();
            String tingkatan = TableKelas.getValueAt(barisYangDipilih, 2).toString();
            String jurusan = TableKelas.getValueAt(barisYangDipilih, 3).toString();
            String waliKelas;
            
            //cek apakah kolom ke-4(wali kelas) berisi data atau tidak
            if (TableKelas.getValueAt(barisYangDipilih, 4)!= null){
                //jika ada data, ambil dan ubah menjadi string 
                waliKelas = TableKelas.getValueAt(barisYangDipilih, 4).toString();
            }else {
                //jika kosong (null), set nilai wali kelas juga null
                waliKelas = null;
            }

            // Isi ke field input
            tKodeKelas.setText(kodeKelas);
            tKodeKelas.setEditable(false);
            tNamaKelas.setText(namaKelas);
            cbTingkatan.setSelectedItem(tingkatan); 
            cbJurusan.setSelectedItem(jurusan);
            cbWaliKelas.setSelectedItem(waliKelas);
          
    }//GEN-LAST:event_TableKelasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelIsi;
    private javax.swing.JTable TableKelas;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bReset;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bTutup;
    private javax.swing.JButton bUbah;
    private javax.swing.JComboBox<String> cbJurusan;
    private javax.swing.JComboBox<String> cbTingkatan;
    private javax.swing.JComboBox<String> cbWaliKelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField tKodeKelas;
    private javax.swing.JTextField tNamaKelas;
    // End of variables declaration//GEN-END:variables

   
}
