// Enum para níveis de dificuldade
enum class Level { BEGINNER, INTERMEDIATE, ADVANCED }

// Classe de usuário
data class User(
    val name: String,
    val email: String,
    val age: Int
)

// Conteúdo educacional com nome, duração e autor
data class EducationalContent(
    val name: String,
    val duration: Int = 60, // Duração padrão de 60 minutos
    val author: String
)

// Interface para comportamento de matrícula
interface Enrollable {
    val enrolledUsers: MutableList<User>
    
    fun enroll(user: User) {
        if (!enrolledUsers.contains(user)) {
            enrolledUsers.add(user)
            println("User ${user.name} enrolled successfully.")
        } else {
            println("User ${user.name} is already enrolled.")
        }
    }

    fun listEnrolledUsers() {
        println("Enrolled Users:")
        if (enrolledUsers.isEmpty()) {
            println("   No users enrolled yet.")
        } else {
            enrolledUsers.forEach { println(" - ${it.name} (${it.email})") }
        }
    }
}

// Classe Curso
class Course(
    val name: String,
    val level: Level,
    val contents: MutableList<EducationalContent> = mutableListOf()
) : Enrollable {
    override val enrolledUsers = mutableListOf<User>()

    // Adicionar conteúdo ao curso
    fun addContent(content: EducationalContent) {
        contents.add(content)
        println("Content ${content.name} added to course $name.")
    }

    // Exibir detalhes do curso
    fun details() {
        println("Course: $name")
        println("Level: $level")
        println("Contents:")
        if (contents.isEmpty()) {
            println("   No content added yet.")
        } else {
            contents.forEach {
                println(" - ${it.name} (${it.duration} minutes) by ${it.author}")
            }
        }
        listEnrolledUsers()
    }
}

// Classe Formação
class Formation(
    val name: String,
    val level: Level,
    val courses: MutableList<Course> = mutableListOf()
) : Enrollable {
    override val enrolledUsers = mutableListOf<User>()

    // Adicionar curso à formação
    fun addCourse(course: Course) {
        if (!courses.contains(course)) {
            courses.add(course)
            println("Course ${course.name} added to formation $name.")
        } else {
            println("Course ${course.name} is already part of formation $name.")
        }
    }

    // Matricular usuário na formação e em todos os cursos
    override fun enroll(user: User) {
        super.enroll(user)
        courses.forEach { it.enroll(user) }
    }

    // Relatório de progresso
    fun progressReport(user: User) {
        println("Progress Report for ${user.name}:")
        val enrolledCourses = courses.filter { it.enrolledUsers.contains(user) }
        if (enrolledCourses.isEmpty()) {
            println("   User is not enrolled in any courses.")
        } else {
            enrolledCourses.forEach { println(" - Enrolled in: ${it.name} (${it.level})") }
        }
    }

    // Exibir detalhes da formação
    fun details() {
        println("Formation: $name")
        println("Level: $level")
        println("Courses:")
        if (courses.isEmpty()) {
            println("   No courses added yet.")
        } else {
            courses.forEach { println(" - ${it.name} (${it.level})") }
        }
        listEnrolledUsers()
    }
}

// Simulação de uso no main
fun main() {
    // Criar usuários
    val user1 = User("Paul", "paul@gmail.com", 35)
    val user2 = User("Cleber", "ckb@gmail.com", 21)

    // Criar conteúdos educacionais
    val content1 = EducationalContent("Introduction to Kotlin", 90, "Professor A")
    val content2 = EducationalContent("Advanced Java", 120, "Professor B")
    val content3 = EducationalContent("Databases Essentials", 80, "Professor C")

    // Criar cursos
    val kotlinCourse = Course("Kotlin Basics", Level.BEGINNER)
    val javaCourse = Course("Java Mastery", Level.INTERMEDIATE)
    val databaseCourse = Course("Database Essentials", Level.BEGINNER)

    // Adicionar conteúdos aos cursos
    kotlinCourse.addContent(content1)
    javaCourse.addContent(content2)
    databaseCourse.addContent(content3)

    // Criar formações
    val fullStackFormation = Formation("Full Stack Developer", Level.ADVANCED)
    val backendFormation = Formation("Backend Developer", Level.INTERMEDIATE)

    // Adicionar cursos às formações
    fullStackFormation.addCourse(kotlinCourse)
    fullStackFormation.addCourse(javaCourse)
    fullStackFormation.addCourse(databaseCourse)

    backendFormation.addCourse(javaCourse)
    backendFormation.addCourse(databaseCourse)

    // Matricular usuários nas formações
    fullStackFormation.enroll(user1)
    backendFormation.enroll(user2)

    // Exibir detalhes
    println("\n--- Full Stack Formation Details ---")
    fullStackFormation.details()

    println("\n--- Backend Formation Details ---")
    backendFormation.details()

    // Relatório de progresso do usuário
    println("\n--- Progress Report ---")
    fullStackFormation.progressReport(user1)
    backendFormation.progressReport(user2)
}
