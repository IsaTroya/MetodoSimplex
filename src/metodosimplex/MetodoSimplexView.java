/*
 * MetodoSimplexView.java
 */
package metodosimplex;

import java.awt.Color;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import metodosimplex.Formulas.Formulas;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.BitSet;
import java.util.concurrent.Delayed;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.w3c.dom.css.RGBColor;

/**
 * The application's main frame.
 */
public class MetodoSimplexView extends FrameView {

    public MetodoSimplexView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = MetodoSimplexApp.getApplication().getMainFrame();
            
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MetodoSimplexApp.getApplication().show(aboutBox);
    }



 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        btnresolver = new java.awt.Button();
        listaResultado = new java.awt.List();
        areaFObj = new javax.swing.JPanel();
        txtfobjetivo = new java.awt.TextField();
        cmbmaxmin = new javax.swing.JComboBox();
        areaRest2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRestricciones = new javax.swing.JTable();
        areaRest = new javax.swing.JPanel();
        cmbsimbolo = new javax.swing.JComboBox();
        txtnsubindices = new java.awt.TextField();
        btnlimpiar = new java.awt.Button();
        btnagregarRestriccion = new java.awt.Button();
        txtnvalor = new java.awt.TextField();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(new java.awt.Dimension(700, 400));
        mainPanel.setMinimumSize(new java.awt.Dimension(600, 300));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 400));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(metodosimplex.MetodoSimplexApp.class).getContext().getResourceMap(MetodoSimplexView.class);
        btnresolver.setLabel(resourceMap.getString("btnresolver.label")); // NOI18N
        btnresolver.setName("btnresolver"); // NOI18N
        btnresolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresolverActionPerformed(evt);
            }
        });

        listaResultado.setName("listaResultado"); // NOI18N

        areaFObj.setBackground(resourceMap.getColor("areaFObj.background")); // NOI18N
        areaFObj.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("areaFObj.border.title"))); // NOI18N
        areaFObj.setToolTipText(resourceMap.getString("areaFObj.toolTipText")); // NOI18N
        areaFObj.setName("areaFObj"); // NOI18N
        areaFObj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                areaFObjMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                areaFObjMouseExited(evt);
            }
        });

        txtfobjetivo.setName("txtfobjetivo"); // NOI18N
        txtfobjetivo.setText(resourceMap.getString("txtfobjetivo.text")); // NOI18N

        cmbmaxmin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Max (Z) =" }));
        cmbmaxmin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cmbmaxmin.setKeySelectionManager(null);
        cmbmaxmin.setName("cmbmaxmin"); // NOI18N
        cmbmaxmin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbmaxminItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout areaFObjLayout = new javax.swing.GroupLayout(areaFObj);
        areaFObj.setLayout(areaFObjLayout);
        areaFObjLayout.setHorizontalGroup(
            areaFObjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, areaFObjLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbmaxmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfobjetivo, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );
        areaFObjLayout.setVerticalGroup(
            areaFObjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(areaFObjLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(areaFObjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbmaxmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfobjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        areaRest2.setBackground(resourceMap.getColor("areaRest2.background")); // NOI18N
        areaRest2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("areaRest2.border.title"))); // NOI18N
        areaRest2.setName("areaRest2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablaRestricciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", ""},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Subindices", "simb", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaRestricciones.setFocusable(false);
        tablaRestricciones.setName("tablaRestricciones"); // NOI18N
        jScrollPane1.setViewportView(tablaRestricciones);
        tablaRestricciones.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("tablaRestricciones.columnModel.title0")); // NOI18N
        tablaRestricciones.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("tablaRestricciones.columnModel.title1")); // NOI18N
        tablaRestricciones.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("tablaRestricciones.columnModel.title2")); // NOI18N

        javax.swing.GroupLayout areaRest2Layout = new javax.swing.GroupLayout(areaRest2);
        areaRest2.setLayout(areaRest2Layout);
        areaRest2Layout.setHorizontalGroup(
            areaRest2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(areaRest2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        areaRest2Layout.setVerticalGroup(
            areaRest2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(areaRest2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        areaRest.setBackground(resourceMap.getColor("areaRest.background")); // NOI18N
        areaRest.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("areaRest.border.title"))); // NOI18N
        areaRest.setName("areaRest"); // NOI18N

        cmbsimbolo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<=", "=" }));
        cmbsimbolo.setName("cmbsimbolo"); // NOI18N

        txtnsubindices.setName("txtnsubindices"); // NOI18N
        txtnsubindices.setText(resourceMap.getString("txtnsubindices.text")); // NOI18N

        btnlimpiar.setLabel(resourceMap.getString("btnlimpiar.label")); // NOI18N
        btnlimpiar.setName("btnlimpiar"); // NOI18N
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });

        btnagregarRestriccion.setLabel(resourceMap.getString("btnagregarRestriccion.label")); // NOI18N
        btnagregarRestriccion.setName("btnagregarRestriccion"); // NOI18N
        btnagregarRestriccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarRestriccionActionPerformed(evt);
            }
        });

        txtnvalor.setName("txtnvalor"); // NOI18N

        javax.swing.GroupLayout areaRestLayout = new javax.swing.GroupLayout(areaRest);
        areaRest.setLayout(areaRestLayout);
        areaRestLayout.setHorizontalGroup(
            areaRestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, areaRestLayout.createSequentialGroup()
                .addComponent(txtnsubindices, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbsimbolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btnlimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(btnagregarRestriccion, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        areaRestLayout.setVerticalGroup(
            areaRestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(areaRestLayout.createSequentialGroup()
                .addComponent(btnagregarRestriccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(areaRestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbsimbolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnsubindices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(areaRest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(areaFObj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnresolver, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                    .addComponent(areaRest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(listaResultado, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(areaRest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnresolver, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(areaFObj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(areaRest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listaResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(196, 196, 196))
        );

        menuBar.setName("menuBar"); // NOI18N

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 628, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
   
    private Problema problema;
    private int nRest = 0;    
    private boolean accionMax = true; 
    private void btnagregarRestriccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarRestriccionActionPerformed
        tablaRestricciones.setValueAt(txtnsubindices.getText(), nRest, 0);
        tablaRestricciones.setValueAt(cmbsimbolo.getSelectedItem().toString(), nRest, 1);
        tablaRestricciones.setValueAt(txtnvalor.getText(), nRest, 2);
        nRest += 1;
        txtnsubindices.setText("");
        txtnvalor.setText("");
    }//GEN-LAST:event_btnagregarRestriccionActionPerformed
    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        limpiarContenido();
        problema.borrarTodo();
    }//GEN-LAST:event_btnlimpiarActionPerformed
    private void limpiarContenido(){
        listaResultado.clear();
        txtfobjetivo.setText(null);
        for (int i = 0; i < nRest; i++) {
            tablaRestricciones.setValueAt(null, i, 0);
            tablaRestricciones.setValueAt(null, i, 1);
            tablaRestricciones.setValueAt(null, i, 2);
        }
        nRest = 0;
    }
    private void btnresolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresolverActionPerformed
        problema = new Problema(accionMax);
        listaResultado.clear();
        problema.borrarTodo();
        problema.setFuncionObjetivo(Formulas.capturar(txtfobjetivo.getText()));
        
        for (int i = 0; i < nRest; i++) {
            problema.nuevaRestriccion(tablaRestricciones.getValueAt(i, 0).toString(), tablaRestricciones.getValueAt(i, 1).toString(), tablaRestricciones.getValueAt(i, 2).toString());
        }
        problema.preparar();
        problema.resolverMetodoSimplex(listaResultado);
    }//GEN-LAST:event_btnresolverActionPerformed
    private void cmbmaxminItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbmaxminItemStateChanged

    }//GEN-LAST:event_cmbmaxminItemStateChanged
    private void areaFObjMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaFObjMouseEntered
    }//GEN-LAST:event_areaFObjMouseEntered
    private void areaFObjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaFObjMouseExited
    }//GEN-LAST:event_areaFObjMouseExited
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaFObj;
    private javax.swing.JPanel areaRest;
    private javax.swing.JPanel areaRest2;
    private java.awt.Button btnagregarRestriccion;
    private java.awt.Button btnlimpiar;
    private java.awt.Button btnresolver;
    private javax.swing.JComboBox cmbmaxmin;
    private javax.swing.JComboBox cmbsimbolo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.List listaResultado;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTable tablaRestricciones;
    private java.awt.TextField txtfobjetivo;
    private java.awt.TextField txtnsubindices;
    private java.awt.TextField txtnvalor;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
