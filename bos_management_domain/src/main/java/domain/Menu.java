package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:菜单
 */
@Entity
@Table(name = "T_MENU")
public class Menu  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menuGenerator")
    @SequenceGenerator(name="menuGenerator", sequenceName="seq_menu", initialValue = 200, allocationSize = 1)
	@Column(name = "C_ID")
	private Integer id;
	@Column(name = "C_NAME")
	private String name; // 菜单名称
	@Column(name = "C_PAGE")
	private String page; // 访问路径
	@Column(name = "C_PRIORITY")
	private Integer priority; // 优先级
	@Column(name = "C_DESCRIPTION")
	private String description; // 描述

	@ManyToMany(mappedBy = "menus")
	private Set<Role> roles = new HashSet<Role>(0);

	//当前菜单下级菜单集合
	//FetchType.EAGER 立即加载   
	@OneToMany(mappedBy = "parentMenu",fetch=FetchType.EAGER) //集合属性，fetch=FetchType.LAZY 默认懒加载
	private Set<Menu> childrenMenus = new HashSet<Menu>();

	//当前菜单所属上级
	@ManyToOne
	@JoinColumn(name = "C_PID")
	private Menu parentMenu;


    /**
     * 为了给新增保存菜单combotree展示文本
     * @return
     */
    public String getText(){
        return name;
    }

    /**
     * 为了给新增保存菜单提供父节点ID
     * @return
     */
    public Integer getpId(){
        if(parentMenu != null){
            //表明有父节点
            return parentMenu.getId();
        }
        return 0;
    }



	//为了返回json中包含children属性(子节点数据)
	public Set<Menu> getChildren(){
		return childrenMenus;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Menu> getChildrenMenus() {
		return childrenMenus;
	}

	public void setChildrenMenus(Set<Menu> childrenMenus) {
		this.childrenMenus = childrenMenus;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

}
