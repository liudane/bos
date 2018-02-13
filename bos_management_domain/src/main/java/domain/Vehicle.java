package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zhaohui on 22/01/2018.
 */
@Entity
@Table(name="T_VEHICLE")
public class Vehicle implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="C_ID")
    private Integer id;
    @Column(name="C_ROUTETYPE")
    private String routeType;
    @Column(name="C_ROUTENAME")
    private String routeName;
    @Column(name = "C_VEHICLENUM")
    private String vehicleNum;
    @Column(name="C_SHIPPER")
    private String shippers;
    @Column(name="C_DRIVER")
    private String driver;
    @Column(name="C_TELEPHONE")
    private String telephone;
    @Column(name="C_VEHICLETYPE")
    private String vehicleType;
    @Column(name="C_TON")
    private String ton;
    @Column(name="C_REMARK")
    private String remark;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", routeType='" + routeType + '\'' +
                ", routeName='" + routeName + '\'' +
                ", vehicleNum='" + vehicleNum + '\'' +
                ", shippers='" + shippers + '\'' +
                ", driver='" + driver + '\'' +
                ", telephone='" + telephone + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", ton='" + ton + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getShippers() {
        return shippers;
    }

    public void setShippers(String shippers) {
        this.shippers = shippers;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
