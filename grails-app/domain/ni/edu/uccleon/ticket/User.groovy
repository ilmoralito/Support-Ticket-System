package ni.edu.uccleon.ticket

import org.grails.databinding.BindUsing

class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	@BindUsing({
		obj, source -> source["fullName"]?.toLowerCase()?.tokenize(" ")*.capitalize().join(" ")
	})
	String fullName
	String email
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	User(String username, String fullName, String email, String password) {
		this()
		this.username = username
		this.fullName = fullName
		this.email = email
		this.password = password
	}

	@Override
	int hashCode() {
		username?.hashCode() ?: 0
	}

	@Override
	boolean equals(other) {
		is(other) || (other instanceof User && other.username == username)
	}

	@Override
	String toString() {
		username
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty("email")) {
			username = email
		}

		if (isDirty('password')) {
			encodePassword()
		}
	}

	def beforeValidate() {
		username = email
		password = "123456seven"
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		fullName blank: false
		email blank: false, unique: true, email: true, validator: { email ->
			def emailTokenized = email.tokenize("@")

			if (emailTokenized[0].tokenize(".").size() != 2 || emailTokenized[1] != "ucc.edu.ni") {
				return "not.valid.email"
			}
		}
		password blank: false
		departments validator: { departments ->
			if (!departments?.size()) {
				"notValid"
			}
		}
	}

	static mapping = {
		password column: '`password`'
	}

	List departments
	List assistances
	static hasMany = [departments: String, assistances: Assistance]
}
