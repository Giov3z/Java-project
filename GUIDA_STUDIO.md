# Guida Completa allo Studio del Progetto University - Refactoring Java

## 📚 Indice

1. [Introduzione al Progetto](#1-introduzione-al-progetto)
2. [Concetti Fondamentali di Java](#2-concetti-fondamentali-di-java)
3. [Principi SOLID](#3-principi-solid)
4. [Design Pattern Utilizzati](#4-design-pattern-utilizzati)
5. [Architettura del Progetto](#5-architettura-del-progetto)
6. [Interfacce e Classi Astratte](#6-interfacce-e-classi-astratte)
7. [Generics in Java](#7-generics-in-java)
8. [Gestione delle Eccezioni](#8-gestione-delle-eccezioni)
9. [Dependency Injection](#9-dependency-injection)
10. [Optional e Programmazione Funzionale](#10-optional-e-programmazione-funzionale)
11. [Domande per l'Interrogazione](#11-domande-per-linterrogazione)
12. [Riassunto Esecutivo](#12-riassunto-esecutivo)

---

## 1. Introduzione al Progetto

### 1.1 Cos'è il Progetto University?

Il progetto University è un'applicazione Java che gestisce entità universitarie:
- **Studenti** (Student)
- **Professori** (Professor)
- **Corsi** (Course)
- **Aule** (Classroom)
- **Iscrizioni** (Enrollment)
- **Voti** (Grade)

### 1.2 Obiettivi del Refactoring

Il refactoring ha trasformato un codice procedurale con alta duplicazione in un sistema orientato agli oggetti ben strutturato, applicando:
- ✅ Principi SOLID
- ✅ Design Pattern consolidati
- ✅ Gestione strutturata delle eccezioni
- ✅ Dependency Injection
- ✅ Interfacce e classi astratte

### 1.3 Struttura del Progetto

```
src/main/java/it/university/
├── MainApp.java                    # Punto di ingresso
├── common/
│   └── EmptyResultException.java   # Eccezione custom
├── model/                          # Entità di dominio
│   ├── Student.java
│   ├── Professor.java
│   ├── Course.java
│   ├── Classroom.java
│   ├── Enrollment.java
│   └── Grade.java
├── repository/                     # Layer di persistenza
│   ├── Repository.java             # Interfaccia generica
│   ├── StudentRepository.java
│   ├── ProfessorRepository.java
│   ├── CourseRepository.java
│   ├── ClassroomRepository.java
│   ├── EnrollmentRepository.java
│   ├── GradeRepository.java
│   └── impl/
│       ├── AbstractInMemoryRepository.java
│       └── EnrollmentRepositoryImpl.java
└── service/                        # Logica di business
    ├── AbstractService.java
    ├── StudentService.java
    ├── ProfessorService.java
    ├── CourseService.java
    ├── ClassroomService.java
    ├── EnrollmentService.java
    └── GradeService.java
```

---

## 2. Concetti Fondamentali di Java

### 2.1 Classi e Oggetti

**Definizione**: Una classe è un template per creare oggetti. Un oggetto è un'istanza di una classe.

**Esempio dal progetto**:
```java
// Classe Student (template)
public class Student {
    private int id;
    private String name;
    private String email;
    
    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

// Creazione oggetto (istanza)
Student alice = new Student(1, "Alice", "alice@mail.com");
```

**Concetti chiave**:
- **Incapsulamento**: I campi sono `private`, accessibili solo tramite metodi pubblici
- **Costruttore**: Metodo speciale per inizializzare gli oggetti
- **Metodi getter**: Permettono l'accesso controllato ai campi privati

### 2.2 Ereditarietà

**Definizione**: Meccanismo che permette a una classe di ereditare proprietà e metodi da un'altra classe.

**Esempio dal progetto**:
```java
// Classe base (superclasse)
public abstract class AbstractService<I, T> {
    private final Repository<I, T> repository;
    
    protected AbstractService(Repository<I, T> repository) {
        this.repository = repository;
    }
    
    public List<T> list() {
        // Logica condivisa
    }
}

// Classe derivata (sottoclasse)
public class StudentService extends AbstractService<Integer, Student> {
    public StudentService(Repository<Integer, Student> repository) {
        super(repository); // Chiama il costruttore della superclasse
    }
}
```

**Vantaggi**:
- ✅ Riuso del codice
- ✅ Riduzione della duplicazione
- ✅ Gerarchia logica

### 2.3 Polimorfismo

**Definizione**: Capacità di un oggetto di assumere diverse forme. Un riferimento a una superclasse può puntare a oggetti di sottoclassi diverse.

**Esempio dal progetto**:
```java
// Interfaccia
Repository<Integer, Student> repo = new StudentRepository();
// Posso sostituire con qualsiasi implementazione di Repository
Repository<Integer, Student> repo2 = new MockStudentRepository(); // Per test
```

**Tipi di polimorfismo**:
1. **Compile-time (Overloading)**: Metodi con stesso nome ma parametri diversi
2. **Runtime (Overriding)**: Sottoclasse ridefinisce metodo della superclasse

### 2.4 Astrazione

**Definizione**: Nascondere i dettagli implementativi e mostrare solo le funzionalità essenziali.

**Strumenti in Java**:
- **Interfacce**: Contratti puri (solo dichiarazioni di metodi)
- **Classi astratte**: Possono contenere implementazioni parziali

---

## 3. Principi SOLID

### 3.1 Single Responsibility Principle (SRP)

**Definizione**: Una classe dovrebbe avere una sola responsabilità, un solo motivo per cambiare.

**Applicazione nel progetto**:

❌ **Prima** (violazione SRP):
```java
public class StudentService {
    private StudentRepository repository = new StudentRepository();
    
    public void registerStudent(Student s) {
        repository.save(s);
    }
    
    public List<Student> list() {
        if (repository.findAll().isEmpty()) {
            System.out.println("Nessuno studente"); // Responsabilità di presentazione!
        }
        return repository.findAll();
    }
}
```

✅ **Dopo** (rispetta SRP):
```java
public class StudentService extends AbstractService<Integer, Student> {
    public void registerStudent(Student student) {
        save(student); // Solo logica di business
    }
    
    public List<Student> list() {
        List<Student> all = repository.findAll();
        if (all.isEmpty()) {
            throw new EmptyResultException(emptyMessage()); // Delega gestione al chiamante
        }
        return all;
    }
}
```

**Responsabilità separate**:
- **Service**: Logica di business
- **Repository**: Persistenza dati
- **MainApp**: Presentazione e orchestrazione

### 3.2 Open/Closed Principle (OCP)

**Definizione**: Le entità software dovrebbero essere aperte all'estensione ma chiuse alla modifica.

**Applicazione nel progetto**:

✅ **Estensibile senza modifiche**:
```java
// Interfaccia stabile (chiusa alla modifica)
public interface Repository<I, T> {
    void save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
}

// Posso aggiungere nuove implementazioni (aperta all'estensione)
public class DatabaseStudentRepository implements Repository<Integer, Student> {
    // Implementazione con database reale
}

public class CachedStudentRepository implements Repository<Integer, Student> {
    // Implementazione con cache
}
```

**Vantaggi**:
- Aggiungere funzionalità senza toccare codice esistente
- Riduce rischio di bug in codice già testato

### 3.3 Liskov Substitution Principle (LSP)

**Definizione**: Gli oggetti di una sottoclasse devono poter sostituire oggetti della superclasse senza alterare il comportamento del programma.

**Applicazione nel progetto**:

```java
// Qualsiasi implementazione di Repository può sostituire l'altra
Repository<Integer, Student> repo1 = new StudentRepository();
Repository<Integer, Student> repo2 = new DatabaseStudentRepository();

// Il service funziona con entrambe
StudentService service1 = new StudentService(repo1);
StudentService service2 = new StudentService(repo2);
```

**Regole LSP**:
- ✅ Sottoclassi devono rispettare il contratto della superclasse
- ✅ Non devono lanciare eccezioni non previste
- ✅ Non devono richiedere precondizioni più forti

### 3.4 Interface Segregation Principle (ISP)

**Definizione**: I client non dovrebbero dipendere da interfacce che non usano.

**Applicazione nel progetto**:

✅ **Interfaccia minimale**:
```java
public interface Repository<I, T> {
    void save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
}
// Solo metodi essenziali, nessun metodo inutilizzato
```

❌ **Violazione ISP** (esempio da evitare):
```java
public interface Repository<I, T> {
    void save(T entity);
    void update(T entity);
    void delete(I id);
    Optional<T> findById(I id);
    List<T> findAll();
    List<T> findByName(String name);
    List<T> findByDate(Date date);
    // Troppi metodi, non tutti necessari per ogni implementazione
}
```

### 3.5 Dependency Inversion Principle (DIP)

**Definizione**: I moduli di alto livello non dovrebbero dipendere da moduli di basso livello. Entrambi dovrebbero dipendere da abstrazioni.

**Applicazione nel progetto**:

❌ **Prima** (dipendenza da implementazione concreta):
```java
public class StudentService {
    private StudentRepository repository = new StudentRepository(); // Dipendenza diretta!
}
```

✅ **Dopo** (dipendenza da astrazione):
```java
public class StudentService extends AbstractService<Integer, Student> {
    // Dipende dall'interfaccia Repository, non dall'implementazione
    public StudentService(Repository<Integer, Student> repository) {
        super(repository);
    }
}

// Nel main, decidiamo quale implementazione usare
Repository<Integer, Student> repo = new StudentRepository();
StudentService service = new StudentService(repo);
```

**Vantaggi**:
- ✅ Facile sostituire implementazioni
- ✅ Testabilità (possiamo iniettare mock)
- ✅ Disaccoppiamento

---

## 4. Design Pattern Utilizzati

### 4.1 Repository Pattern

**Definizione**: Astrae la logica di accesso ai dati, fornendo un'interfaccia simile a una collezione.

**Implementazione nel progetto**:

```java
// Interfaccia Repository (contratto)
public interface Repository<I, T> {
    void save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
}

// Implementazione in-memory
public abstract class AbstractInMemoryRepository<I, T> implements Repository<I, T> {
    private final Map<I, T> store = new HashMap<>();
    
    @Override
    public void save(T entity) {
        store.put(extractId(entity), entity);
    }
    
    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }
    
    protected abstract I extractId(T entity);
}
```

**Vantaggi**:
- ✅ Separa logica di business da persistenza
- ✅ Facile cambiare storage (da in-memory a database)
- ✅ Testabilità migliorata

### 4.2 Template Method Pattern

**Definizione**: Definisce lo scheletro di un algoritmo in una superclasse, permettendo alle sottoclassi di ridefinire alcuni passi senza cambiare la struttura.

**Implementazione nel progetto**:

```java
// Classe astratta con template method
public abstract class AbstractService<I, T> {
    public List<T> list() {
        List<T> all = repository.findAll();
        if (all.isEmpty()) {
            throw new EmptyResultException(emptyMessage()); // Chiama metodo astratto
        }
        return all;
    }
    
    // Template method: ogni sottoclasse fornisce il proprio messaggio
    protected abstract String emptyMessage();
}

// Implementazione concreta
public class StudentService extends AbstractService<Integer, Student> {
    @Override
    protected String emptyMessage() {
        return "Nessuno studente presente";
    }
}
```

**Vantaggi**:
- ✅ Riuso del codice comune
- ✅ Personalizzazione controllata
- ✅ Evita duplicazione

### 4.3 Dependency Injection Pattern

**Definizione**: Le dipendenze di una classe vengono fornite dall'esterno invece di essere create internamente.

**Implementazione nel progetto**:

```java
// Service riceve dipendenze via costruttore
public class StudentService extends AbstractService<Integer, Student> {
    public StudentService(Repository<Integer, Student> repository) {
        super(repository); // Injection via costruttore
    }
}

// Nel main, creiamo e iniettiamo le dipendenze
StudentRepository studentRepository = new StudentRepository();
StudentService studentService = new StudentService(studentRepository);
```

**Tipi di Injection**:
1. **Constructor Injection** (usata nel progetto): Dipendenze passate al costruttore
2. **Setter Injection**: Dipendenze impostate tramite metodi setter
3. **Field Injection**: Dipendenze iniettate direttamente nei campi (richiede framework)

**Vantaggi**:
- ✅ Testabilità (possiamo iniettare mock)
- ✅ Flessibilità (cambiare implementazioni facilmente)
- ✅ Disaccoppiamento

### 4.4 Strategy Pattern

**Definizione**: Definisce una famiglia di algoritmi, li incapsula e li rende intercambiabili.

**Implementazione nel progetto**:

```java
// Diverse strategie per estrarre l'ID
public class StudentRepository extends AbstractInMemoryRepository<Integer, Student> {
    @Override
    protected Integer extractId(Student entity) {
        return entity.getId(); // Strategia: usa campo id
    }
}

public class ClassroomRepository extends AbstractInMemoryRepository<String, Classroom> {
    @Override
    protected String extractId(Classroom entity) {
        return entity.getCode(); // Strategia: usa campo code
    }
}

public class GradeRepository extends AbstractInMemoryRepository<String, Grade> {
    @Override
    protected String extractId(Grade entity) {
        return entity.getStudentId() + ":" + entity.getCourseId(); // Strategia: chiave composta
    }
}
```

---

## 5. Architettura del Progetto

### 5.1 Architettura a Livelli (Layered Architecture)

Il progetto segue un'architettura a 4 livelli:

```
┌─────────────────────────────────────┐
│     Presentation Layer (MainApp)    │  ← Gestisce input/output
├─────────────────────────────────────┤
│     Service Layer (Services)        │  ← Logica di business
├─────────────────────────────────────┤
│     Repository Layer (Repositories) │  ← Accesso ai dati
├─────────────────────────────────────┤
│     Model Layer (Entities)          │  ← Entità di dominio
└─────────────────────────────────────┘
```

**Responsabilità di ogni layer**:

1. **Model Layer** (`model/`):
   - Definisce le entità di dominio
   - Contiene solo dati e logica di validazione
   - Esempio: `Student`, `Professor`, `Course`

2. **Repository Layer** (`repository/`):
   - Gestisce la persistenza dei dati
   - Astrae il meccanismo di storage
   - Esempio: `StudentRepository`, `CourseRepository`

3. **Service Layer** (`service/`):
   - Contiene la logica di business
   - Coordina operazioni tra repository
   - Gestisce transazioni e validazioni complesse
   - Esempio: `StudentService`, `CourseService`

4. **Presentation Layer** (`MainApp`):
   - Gestisce interazione con l'utente
   - Formatta output
   - Gestisce eccezioni per presentazione

### 5.2 Flusso di Esecuzione

```
1. MainApp crea repository
   ↓
2. MainApp inietta repository nei service
   ↓
3. MainApp chiama metodi dei service
   ↓
4. Service esegue logica di business
   ↓
5. Service chiama repository per persistenza
   ↓
6. Repository salva/recupera dati
   ↓
7. Service restituisce risultato o lancia eccezione
   ↓
8. MainApp gestisce risultato/eccezione e presenta output
```

**Esempio concreto**:
```java
// 1. Creazione repository
StudentRepository studentRepository = new StudentRepository();

// 2. Injection nel service
StudentService studentService = new StudentService(studentRepository);

// 3. Chiamata metodo service
try {
    List<Student> students = studentService.list(); // 4-7: Service → Repository
    students.forEach(System.out::println); // 8: Presentazione
} catch (EmptyResultException e) {
    System.out.println(e.getMessage()); // 8: Gestione eccezione
}
```

---

## 6. Interfacce e Classi Astratte

### 6.1 Interfacce in Java

**Definizione**: Un'interfaccia è un contratto che specifica quali metodi una classe deve implementare, senza fornire l'implementazione.

**Sintassi**:
```java
public interface Repository<I, T> {
    void save(T entity);           // Metodo astratto (senza corpo)
    Optional<T> findById(I id);    // Metodo astratto
    List<T> findAll();             // Metodo astratto
}
```

**Caratteristiche**:
- ✅ Tutti i metodi sono implicitamente `public abstract`
- ✅ Non può contenere campi di istanza (solo costanti `public static final`)
- ✅ Una classe può implementare multiple interfacce
- ✅ Da Java 8: può contenere metodi `default` con implementazione

**Quando usare interfacce**:
- Definire contratti puri
- Permettere ereditarietà multipla (una classe può implementare più interfacce)
- Disaccoppiare completamente interfaccia da implementazione

### 6.2 Classi Astratte in Java

**Definizione**: Una classe astratta è una classe che non può essere istanziata direttamente e può contenere metodi astratti e concreti.

**Sintassi**:
```java
public abstract class AbstractService<I, T> {
    private final Repository<I, T> repository; // Campo di istanza
    
    // Costruttore
    protected AbstractService(Repository<I, T> repository) {
        this.repository = repository;
    }
    
    // Metodo concreto (con implementazione)
    public List<T> list() {
        List<T> all = repository.findAll();
        if (all.isEmpty()) {
            throw new EmptyResultException(emptyMessage());
        }
        return all;
    }
    
    // Metodo astratto (senza implementazione)
    protected abstract String emptyMessage();
}
```

**Caratteristiche**:
- ✅ Può contenere campi di istanza
- ✅ Può contenere costruttori
- ✅ Può contenere metodi concreti e astratti
- ✅ Una classe può estendere solo una classe astratta (ereditarietà singola)
- ✅ Può implementare interfacce

**Quando usare classi astratte**:
- Condividere codice comune tra sottoclassi
- Definire comportamento parziale
- Fornire implementazione di default

### 6.3 Interfacce vs Classi Astratte

| Aspetto | Interfaccia | Classe Astratta |
|---------|-------------|-----------------|
| **Metodi** | Solo astratti (o default da Java 8) | Astratti e concreti |
| **Campi** | Solo costanti | Campi di istanza |
| **Costruttori** | No | Sì |
| **Ereditarietà** | Multipla (implements) | Singola (extends) |
| **Uso** | Contratti puri | Condivisione codice |

**Esempio dal progetto**:

```java
// Interfaccia: contratto puro
public interface Repository<I, T> {
    void save(T entity);
    List<T> findAll();
}

// Classe astratta: implementazione parziale + template method
public abstract class AbstractInMemoryRepository<I, T> implements Repository<I, T> {
    private final Map<I, T> store = new HashMap<>(); // Campo di istanza
    
    @Override
    public void save(T entity) { // Implementazione concreta
        store.put(extractId(entity), entity);
    }
    
    protected abstract I extractId(T entity); // Metodo astratto
}

// Classe concreta: implementazione completa
public class StudentRepository extends AbstractInMemoryRepository<Integer, Student> {
    @Override
    protected Integer extractId(Student entity) {
        return entity.getId();
    }
}
```

---

## 7. Generics in Java

### 7.1 Cos'è un Generic?

**Definizione**: I generics permettono di scrivere codice che funziona con diversi tipi, mantenendo la type-safety a compile-time.

**Sintassi base**:
```java
// Classe generica
public class Box<T> {
    private T content;
    
    public void set(T content) {
        this.content = content;
    }
    
    public T get() {
        return content;
    }
}

// Uso
Box<String> stringBox = new Box<>();
stringBox.set("Hello");
String value = stringBox.get(); // No cast necessario!

Box<Integer> intBox = new Box<>();
intBox.set(42);
Integer number = intBox.get();
```

### 7.2 Generics nel Progetto

**Repository generico**:
```java
public interface Repository<I, T> {
    //                    ↑  ↑
    //                    |  └─ T = Tipo dell'entità (Student, Professor, ecc.)
    //                    └──── I = Tipo dell'identificativo (Integer, String, ecc.)
    
    void save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
}
```

**Implementazioni concrete**:
```java
// StudentRepository: I=Integer, T=Student
public class StudentRepository extends AbstractInMemoryRepository<Integer, Student> {
    @Override
    protected Integer extractId(Student entity) {
        return entity.getId();
    }
}

// ClassroomRepository: I=String, T=Classroom
public class ClassroomRepository extends AbstractInMemoryRepository<String, Classroom> {
    @Override
    protected String extractId(Classroom entity) {
        return entity.getCode();
    }
}
```

### 7.3 Vantaggi dei Generics

✅ **Type Safety**: Errori rilevati a compile-time
```java
Repository<Integer, Student> repo = new StudentRepository();
repo.save(new Student(1, "Alice", "alice@mail.com")); // OK
// repo.save(new Professor(1, "Dr. Rossi", "CS")); // ERRORE DI COMPILAZIONE!
```

✅ **Riuso del codice**: Una sola implementazione per tutti i tipi
```java
// Invece di scrivere StudentRepository, ProfessorRepository, CourseRepository...
// Scriviamo una sola AbstractInMemoryRepository<I, T>
```

✅ **Eliminazione cast**: Il compilatore sa già il tipo
```java
// Senza generics
List list = new ArrayList();
list.add("Hello");
String s = (String) list.get(0); // Cast necessario

// Con generics
List<String> list = new ArrayList<>();
list.add("Hello");
String s = list.get(0); // No cast!
```

### 7.4 Bounded Type Parameters

**Definizione**: Limitare i tipi che possono essere usati come parametri generici.

**Esempio**:
```java
// T deve essere un Number o una sua sottoclasse
public class Calculator<T extends Number> {
    public double sum(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }
}

Calculator<Integer> intCalc = new Calculator<>(); // OK
Calculator<Double> doubleCalc = new Calculator<>(); // OK
// Calculator<String> stringCalc = new Calculator<>(); // ERRORE!
```

---

## 8. Gestione delle Eccezioni

### 8.1 Tipi di Eccezioni in Java

```
Throwable
├── Error (errori di sistema, non recuperabili)
│   └── OutOfMemoryError, StackOverflowError, ecc.
└── Exception
    ├── RuntimeException (unchecked, non obbligatorie da gestire)
    │   ├── NullPointerException
    │   ├── IllegalArgumentException
    │   └── EmptyResultException (custom nel progetto)
    └── Checked Exceptions (obbligatorie da gestire)
        ├── IOException
        ├── SQLException
        └── ecc.
```

### 8.2 Eccezione Custom: EmptyResultException

**Definizione**:
```java
package it.university.common;

public class EmptyResultException extends RuntimeException {
    public EmptyResultException(String message) {
        super(message);
    }
}
```

**Perché RuntimeException?**
- ✅ Non obbliga il chiamante a gestirla (unchecked)
- ✅ Più flessibile per situazioni non critiche
- ✅ Evita try-catch eccessivi nel codice

### 8.3 Uso nel Progetto

**Nel Service** (lancia l'eccezione):
```java
public abstract class AbstractService<I, T> {
    public List<T> list() {
        List<T> all = repository.findAll();
        if (all.isEmpty()) {
            throw new EmptyResultException(emptyMessage()); // Lancia eccezione
        }
        return all;
    }
    
    protected abstract String emptyMessage();
}
```

**Nel MainApp** (cattura l'eccezione):
```java
private static <T> void printSection(String title, SupplierWithEmptyException<List<T>> supplier) {
    System.out.println("\n" + title + ":");
    try {
        supplier.get().forEach(System.out::println);
    } catch (EmptyResultException e) {
        System.out.println(e.getMessage()); // Gestisce l'eccezione
    }
}
```

### 8.4 Vantaggi della Gestione Strutturata

❌ **Prima** (gestione implicita):
```java
public List<Student> list() {
    if (repository.findAll().isEmpty()) {
        System.out.println("Nessuno studente"); // Stampa diretta!
    }
    return repository.findAll();
}
```

**Problemi**:
- Service si occupa di presentazione (viola SRP)
- Difficile testare
- Non flessibile (sempre stampa, mai log o UI)

✅ **Dopo** (gestione esplicita):
```java
public List<Student> list() {
    List<Student> all = repository.findAll();
    if (all.isEmpty()) {
        throw new EmptyResultException("Nessuno studente presente");
    }
    return all;
}
```

**Vantaggi**:
- ✅ Separazione responsabilità
- ✅ Chiamante decide come gestire
- ✅ Testabile
- ✅ Flessibile (stampa, log, UI, ecc.)

---

## 9. Dependency Injection

### 9.1 Cos'è la Dependency Injection?

**Definizione**: Pattern in cui le dipendenze di una classe vengono fornite dall'esterno invece di essere create internamente.

**Analogia**: Invece di costruire la propria auto, ricevi un'auto già pronta (e puoi scegliere quale modello).

### 9.2 Senza Dependency Injection

❌ **Tight Coupling** (accoppiamento stretto):
```java
public class StudentService {
    private StudentRepository repository = new StudentRepository(); // Crea internamente!
    
    public void registerStudent(Student s) {
        repository.save(s);
    }
}
```

**Problemi**:
- ❌ Impossibile sostituire il repository
- ❌ Difficile testare (non posso usare mock)
- ❌ Service dipende da implementazione concreta

### 9.3 Con Dependency Injection

✅ **Loose Coupling** (accoppiamento debole):
```java
public class StudentService extends AbstractService<Integer, Student> {
    public StudentService(Repository<Integer, Student> repository) {
        super(repository); // Riceve dall'esterno!
    }
    
    public void registerStudent(Student student) {
        save(student);
    }
}

// Nel main
StudentRepository studentRepository = new StudentRepository();
StudentService studentService = new StudentService(studentRepository);
```

**Vantaggi**:
- ✅ Facile sostituire implementazioni
- ✅ Testabile (posso iniettare mock)
- ✅ Service dipende da interfaccia, non da implementazione

### 9.4 Esempio di Test con Mock

```java
// Mock repository per test
class MockStudentRepository implements Repository<Integer, Student> {
    private List<Student> students = new ArrayList<>();
    
    @Override
    public void save(Student entity) {
        students.add(entity);
    }
    
    @Override
    public List<Student> findAll() {
        return students;
    }
}

// Test
@Test
public void testRegisterStudent() {
    // Arrange
    Repository<Integer, Student> mockRepo = new MockStudentRepository();
    StudentService service = new StudentService(mockRepo); // Injection del mock!
    
    // Act
    service.registerStudent(new Student(1, "Alice", "alice@mail.com"));
    
    // Assert
    assertEquals(1, service.list().size());
}
```

### 9.5 Tipi di Dependency Injection

1. **Constructor Injection** (usata nel progetto):
```java
public class StudentService {
    private final Repository<Integer, Student> repository;
    
    public StudentService(Repository<Integer, Student> repository) {
        this.repository = repository; // Injection via costruttore
    }
}
```

2. **Setter Injection**:
```java
public class StudentService {
    private Repository<Integer, Student> repository;
    
    public void setRepository(Repository<Integer, Student> repository) {
        this.repository = repository; // Injection via setter
    }
}
```

3. **Field Injection** (richiede framework come Spring):
```java
public class StudentService {
    @Autowired // Annotation di Spring
    private Repository<Integer, Student> repository;
}
```

**Raccomandazione**: Constructor Injection è preferibile perché:
- ✅ Dipendenze obbligatorie (non possono essere null)
- ✅ Immutabilità (campo `final`)
- ✅ Testabilità senza framework

---

## 10. Optional e Programmazione Funzionale

### 10.1 Cos'è Optional?

**Definizione**: Container che può contenere o meno un valore, evitando `NullPointerException`.

**Sintassi**:
```java
Optional<Student> optionalStudent = repository.findById(1);

// Verifica se presente
if (optionalStudent.isPresent()) {
    Student student = optionalStudent.get();
    System.out.println(student);
}

// Modo funzionale (preferito)
optionalStudent.ifPresent(student -> System.out.println(student));
```

### 10.2 Uso nel Progetto

**Nel Repository**:
```java
public interface Repository<I, T> {
    Optional<T> findById(I id); // Restituisce Optional invece di null
}

public abstract class AbstractInMemoryRepository<I, T> implements Repository<I, T> {
    @Override
    public Optional<T> findById(I id) {
        return Optional.ofNullable(store.get(id)); // Wrappa in Optional
    }
}
```

**Nel Service**:
```java
public class CourseService extends AbstractService<Integer, Course> {
    public void assignProfessor(int courseId, int professorId) {
        courseRepository.findById(courseId)
                .ifPresent(course -> { // Esegue solo se presente
                    course.setProfessorId(professorId);
                    save(course);
                });
    }
}
```

### 10.3 Vantaggi di Optional

❌ **Senza Optional** (rischio NullPointerException):
```java
public void assignProfessor(int courseId, int professorId) {
    Course course = courseRepository.findById(courseId);
    if (course != null) { // Controllo manuale
        course.setProfessorId(professorId);
        save(course);
    }
}
```

✅ **Con Optional** (sicuro e funzionale):
```java
public void assignProfessor(int courseId, int professorId) {
    courseRepository.findById(courseId)
            .ifPresent(course -> {
                course.setProfessorId(professorId);
                save(course);
            });
}
```

### 10.4 Metodi Utili di Optional

```java
Optional<Student> opt = repository.findById(1);

// Verifica presenza
boolean present = opt.isPresent();
boolean empty = opt.isEmpty();

// Ottieni valore
Student student = opt.get(); // Lancia eccezione se vuoto!
Student student = opt.orElse(new Student(0, "Default", "default@mail.com")); // Valore di default
Student student = opt.orElseThrow(() -> new RuntimeException("Not found")); // Lancia eccezione custom

// Operazioni funzionali
opt.ifPresent(s -> System.out.println(s)); // Esegue se presente
opt.map(Student::getName).ifPresent(System.out::println); // Trasforma e usa
```

### 10.5 Programmazione Funzionale in Java

**Lambda Expressions**:
```java
// Sintassi tradizionale
students.forEach(new Consumer<Student>() {
    @Override
    public void accept(Student student) {
        System.out.println(student);
    }
});

// Lambda expression
students.forEach(student -> System.out.println(student));

// Method reference
students.forEach(System.out::println);
```

**Functional Interfaces nel progetto**:
```java
@FunctionalInterface
private interface SupplierWithEmptyException<T> {
    T get() throws EmptyResultException;
}

// Uso
printSection("Studenti", studentService::list); // Method reference
```

---

## 11. Domande per l'Interrogazione

### 11.1 Domande sui Principi SOLID

**Q1**: Cos'è il Single Responsibility Principle e come è applicato nel progetto?

**Risposta**: Il SRP afferma che una classe dovrebbe avere una sola responsabilità. Nel progetto:
- **Repository**: Solo persistenza dati
- **Service**: Solo logica di business
- **MainApp**: Solo presentazione
Prima del refactoring, i service stampavano direttamente (violazione SRP). Dopo, lanciano eccezioni e il MainApp gestisce la presentazione.

---

**Q2**: Spiega il Dependency Inversion Principle con un esempio dal progetto.

**Risposta**: Il DIP dice che i moduli di alto livello devono dipendere da abstrazioni, non da implementazioni concrete. Nel progetto:
```java
// StudentService dipende dall'interfaccia Repository, non da StudentRepository
public class StudentService extends AbstractService<Integer, Student> {
    public StudentService(Repository<Integer, Student> repository) {
        super(repository);
    }
}
```
Questo permette di sostituire facilmente l'implementazione (es. da in-memory a database) senza modificare il service.

---

**Q3**: Come il progetto rispetta l'Open/Closed Principle?

**Risposta**: Il progetto è aperto all'estensione ma chiuso alla modifica. Posso aggiungere nuove implementazioni di `Repository` (es. `DatabaseStudentRepository`) senza modificare il codice esistente. L'interfaccia `Repository` è stabile e i service dipendono da essa.

---

### 11.2 Domande sui Design Pattern

**Q4**: Cos'è il Repository Pattern e quali vantaggi offre?

**Risposta**: Il Repository Pattern astrae la logica di accesso ai dati, fornendo un'interfaccia simile a una collezione. Vantaggi:
- Separa logica di business da persistenza
- Facile cambiare storage (da in-memory a database)
- Testabilità migliorata (posso usare mock)

---

**Q5**: Spiega il Template Method Pattern con un esempio dal progetto.

**Risposta**: Il Template Method definisce lo scheletro di un algoritmo, permettendo alle sottoclassi di personalizzare alcuni passi. Nel progetto:
```java
public abstract class AbstractService<I, T> {
    public List<T> list() {
        List<T> all = repository.findAll();
        if (all.isEmpty()) {
            throw new EmptyResultException(emptyMessage()); // Chiama metodo astratto
        }
        return all;
    }
    
    protected abstract String emptyMessage(); // Personalizzato dalle sottoclassi
}
```
Ogni service fornisce il proprio messaggio di errore senza duplicare la logica di `list()`.

---

**Q6**: Cos'è la Dependency Injection e perché è importante?

**Risposta**: La Dependency Injection è un pattern in cui le dipendenze vengono fornite dall'esterno. È importante perché:
- Migliora la testabilità (posso iniettare mock)
- Riduce l'accoppiamento (dipendo da interfacce)
- Aumenta la flessibilità (facile sostituire implementazioni)

Nel progetto, i service ricevono i repository via costruttore invece di crearli internamente.

---

### 11.3 Domande su Interfacce e Classi Astratte

**Q7**: Qual è la differenza tra interfaccia e classe astratta?

**Risposta**:
- **Interfaccia**: Contratto puro, solo metodi astratti (o default), no campi di istanza, ereditarietà multipla
- **Classe astratta**: Può contenere metodi concreti e astratti, campi di istanza, costruttori, ereditarietà singola

Nel progetto:
- `Repository<I, T>` è un'interfaccia (contratto)
- `AbstractInMemoryRepository<I, T>` è una classe astratta (implementazione parziale)

---

**Q8**: Quando usare un'interfaccia e quando una classe astratta?

**Risposta**:
- **Interfaccia**: Quando voglio definire un contratto puro senza implementazione
- **Classe astratta**: Quando voglio condividere codice comune tra sottoclassi

Esempio: `Repository` è un'interfaccia perché definisce solo il contratto. `AbstractInMemoryRepository` è una classe astratta perché fornisce implementazione comune per tutti i repository in-memory.

---

### 11.4 Domande sui Generics

**Q9**: Cosa sono i generics e quali vantaggi offrono?

**Risposta**: I generics permettono di scrivere codice che funziona con diversi tipi, mantenendo la type-safety. Vantaggi:
- Type safety (errori a compile-time)
- Riuso del codice (una sola implementazione per tutti i tipi)
- Eliminazione cast

Nel progetto, `Repository<I, T>` è generico: `I` è il tipo dell'ID, `T` è il tipo dell'entità.

---

**Q10**: Spiega la sintassi `Repository<Integer, Student>`.

**Risposta**: È un'istanza generica di `Repository` dove:
- `Integer` è il tipo dell'identificativo (parametro `I`)
- `Student` è il tipo dell'entità (parametro `T`)

Questo significa che il repository gestisce studenti identificati da interi.

---

### 11.5 Domande sulla Gestione delle Eccezioni

**Q11**: Cos'è `EmptyResultException` e perché è stata creata?

**Risposta**: `EmptyResultException` è un'eccezione custom che estende `RuntimeException`. È stata creata per gestire il caso in cui una query non produce risultati. Prima del refactoring, i service stampavano direttamente messaggi (violazione SRP). Ora lanciano questa eccezione e il chiamante decide come gestirla.

---

**Q12**: Qual è la differenza tra checked e unchecked exceptions?

**Risposta**:
- **Checked**: Obbligatorie da gestire (es. `IOException`), estendono `Exception`
- **Unchecked**: Non obbligatorie da gestire (es. `NullPointerException`), estendono `RuntimeException`

`EmptyResultException` è unchecked perché estende `RuntimeException`. Questo la rende più flessibile: il chiamante può scegliere se gestirla o meno.

---

### 11.6 Domande su Optional

**Q13**: Cos'è `Optional` e perché è utile?

**Risposta**: `Optional` è un container che può contenere o meno un valore. È utile per evitare `NullPointerException`. Nel progetto:
```java
Optional<Course> optionalCourse = courseRepository.findById(courseId);
optionalCourse.ifPresent(course -> {
    course.setProfessorId(professorId);
    save(course);
});
```
Invece di controllare manualmente se il corso è null, uso `ifPresent()` che esegue il codice solo se il valore è presente.

---

**Q14**: Quali sono i metodi principali di `Optional`?

**Risposta**:
- `isPresent()`: Verifica se il valore è presente
- `isEmpty()`: Verifica se il valore è assente
- `get()`: Ottiene il valore (lancia eccezione se vuoto)
- `orElse(T other)`: Restituisce il valore o un default
- `orElseThrow()`: Restituisce il valore o lancia eccezione
- `ifPresent(Consumer)`: Esegue azione se presente
- `map(Function)`: Trasforma il valore

---

### 11.7 Domande sull'Architettura

**Q15**: Descrivi l'architettura a livelli del progetto.

**Risposta**: Il progetto segue un'architettura a 4 livelli:
1. **Model Layer**: Entità di dominio (`Student`, `Professor`, ecc.)
2. **Repository Layer**: Accesso ai dati (`StudentRepository`, ecc.)
3. **Service Layer**: Logica di business (`StudentService`, ecc.)
4. **Presentation Layer**: Interazione utente (`MainApp`)

Ogni livello ha responsabilità specifiche e comunica solo con i livelli adiacenti.

---

**Q16**: Qual è il flusso di esecuzione quando si registra uno studente?

**Risposta**:
1. `MainApp` chiama `studentService.registerStudent(student)`
2. `StudentService` chiama `save(student)` (ereditato da `AbstractService`)
3. `AbstractService` chiama `repository.save(student)`
4. `StudentRepository` (estende `AbstractInMemoryRepository`) salva lo studente nella Map
5. Il controllo torna al `MainApp`

---

### 11.8 Domande sul Refactoring

**Q17**: Quali erano i principali problemi della versione originale?

**Risposta**:
- **Tight coupling**: Service creavano repository internamente
- **Violazione SRP**: Service stampavano direttamente
- **Duplicazione**: Ogni repository duplicava logica di Map/List
- **Non testabile**: Impossibile iniettare mock
- **Non estensibile**: Difficile cambiare storage

---

**Q18**: Quali miglioramenti ha portato il refactoring?

**Risposta**:
- **Loose coupling**: Dependency injection
- **Rispetto SRP**: Separazione responsabilità
- **DRY**: Classi astratte eliminano duplicazione
- **Testabilità**: Facile iniettare mock
- **Estensibilità**: Facile aggiungere nuove implementazioni
- **Manutenibilità**: Codice più pulito e organizzato

---

### 11.9 Domande Avanzate

**Q19**: Come aggiungeresti persistenza su database al progetto?

**Risposta**:
1. Creare `DatabaseStudentRepository implements Repository<Integer, Student>`
2. Implementare i metodi usando JDBC o JPA
3. Nel `MainApp`, sostituire:
```java
Repository<Integer, Student> repo = new DatabaseStudentRepository();
StudentService service = new StudentService(repo);
```
Nessuna modifica necessaria al service grazie alla Dependency Injection!

---

**Q20**: Come testeresti `StudentService` con un mock repository?

**Risposta**:
```java
@Test
public void testRegisterStudent() {
    // Arrange
    Repository<Integer, Student> mockRepo = new MockStudentRepository();
    StudentService service = new StudentService(mockRepo);
    Student student = new Student(1, "Alice", "alice@mail.com");
    
    // Act
    service.registerStudent(student);
    
    // Assert
    List<Student> students = service.list();
    assertEquals(1, students.size());
    assertEquals("Alice", students.get(0).getName());
}
```

---

## 12. Riassunto Esecutivo

### 12.1 Cosa Abbiamo Imparato

#### Concetti Fondamentali
- ✅ **OOP**: Classi, oggetti, ereditarietà, polimorfismo, astrazione
- ✅ **SOLID**: 5 principi per codice manutenibile
- ✅ **Design Pattern**: Repository, Template Method, Dependency Injection, Strategy
- ✅ **Generics**: Codice type-safe e riusabile
- ✅ **Eccezioni**: Gestione strutturata degli errori
- ✅ **Optional**: Evitare NullPointerException
- ✅ **Architettura**: Layered architecture

#### Tecniche Avanzate
- ✅ **Interfacce vs Classi Astratte**: Quando usare l'una o l'altra
- ✅ **Dependency Injection**: Disaccoppiamento e testabilità
- ✅ **Template Method**: Riuso codice con personalizzazione
- ✅ **Programmazione Funzionale**: Lambda, method reference, Optional

### 12.2 Struttura del Progetto

```
University Management System
│
├── Model Layer (Entità)
│   ├── Student, Professor, Course
│   ├── Classroom, Enrollment, Grade
│   └── Responsabilità: Dati di dominio
│
├── Repository Layer (Persistenza)
│   ├── Repository<I, T> (interfaccia)
│   ├── AbstractInMemoryRepository<I, T> (classe astratta)
│   ├── StudentRepository, ProfessorRepository, ecc.
│   └── Responsabilità: Accesso ai dati
│
├── Service Layer (Business Logic)
│   ├── AbstractService<I, T> (classe astratta)
│   ├── StudentService, ProfessorService, ecc.
│   └── Responsabilità: Logica di business
│
├── Common (Utilities)
│   ├── EmptyResultException
│   └── Responsabilità: Eccezioni custom
│
└── Presentation Layer
    ├── MainApp
    └── Responsabilità: Interazione utente
```

### 12.3 Principi Chiave

1. **Separazione delle Responsabilità**
   - Ogni classe ha un solo compito
   - Facilita manutenzione e testing

2. **Dipendenza da Abstrazioni**
   - Dipendere da interfacce, non da implementazioni
   - Facilita estensibilità e sostituzione

3. **Riuso del Codice**
   - Classi astratte per codice comune
   - Generics per type-safety

4. **Gestione Strutturata degli Errori**
   - Eccezioni custom per casi specifici
   - Separazione tra logica e presentazione

### 12.4 Metriche di Miglioramento

| Aspetto | Prima | Dopo | Miglioramento |
|---------|-------|------|---------------|
| Linee di codice repository | ~60 | ~30 | -50% |
| Linee di codice service | ~70 | ~40 | -43% |
| Duplicazione | Alta | Minima | -80% |
| Testabilità | Bassa | Alta | ✓ |
| Estensibilità | Difficile | Facile | ✓ |
| Manutenibilità | Bassa | Alta | ✓ |

### 12.5 Punti Chiave per l'Interrogazione

**Devi saper spiegare**:
1. ✅ I 5 principi SOLID con esempi dal progetto
2. ✅ Differenza tra interfaccia e classe astratta
3. ✅ Come funziona la Dependency Injection
4. ✅ Cos'è il Repository Pattern
5. ✅ Come funzionano i generics
6. ✅ Perché è stata creata `EmptyResultException`
7. ✅ Come funziona `Optional`
8. ✅ L'architettura a livelli del progetto
9. ✅ Il flusso di esecuzione di un'operazione
10. ✅ I vantaggi del refactoring

**Devi saper fare**:
1. ✅ Creare un'interfaccia generica
2. ✅ Implementare una classe astratta
3. ✅ Usare la Dependency Injection
4. ✅ Gestire eccezioni custom
5. ✅ Usare `Optional` correttamente
6. ✅ Applicare il Template Method Pattern
7. ✅ Estendere il progetto con nuove entità

### 12.6 Comandi Utili

**Compilare il progetto**:
```bash
gradle build
```

**Eseguire l'applicazione**:
```bash
gradle run
# oppure
java -cp build/classes/java/main it.university.MainApp
```

**Pulire e ricompilare**:
```bash
gradle clean build
```

### 12.7 Risorse Aggiuntive

**Libri consigliati**:
- "Clean Code" di Robert C. Martin
- "Design Patterns" di Gang of Four
- "Effective Java" di Joshua Bloch

**Concetti da approfondire**:
- Spring Framework (Dependency Injection avanzata)
- JPA/Hibernate (Persistenza database)
- Unit Testing con JUnit e Mockito
- Stream API di Java 8+

---

## 📝 Checklist per l'Interrogazione

Prima dell'interrogazione, assicurati di:

- [ ] Saper spiegare ogni principio SOLID con esempi
- [ ] Conoscere la differenza tra interfaccia e classe astratta
- [ ] Saper descrivere l'architettura del progetto
- [ ] Capire come funziona la Dependency Injection
- [ ] Saper spiegare i design pattern utilizzati
- [ ] Conoscere i vantaggi dei generics
- [ ] Saper gestire eccezioni custom
- [ ] Capire come funziona Optional
- [ ] Saper descrivere il flusso di esecuzione
- [ ] Conoscere i miglioramenti del refactoring

---

## 🎯 Conclusione

Questo progetto dimostra come applicare principi di ingegneria del software per trasformare codice procedurale in un sistema orientato agli oggetti ben strutturato. I concetti appresi sono fondamentali per lo sviluppo di applicazioni enterprise e sono richiesti in qualsiasi contesto professionale.

**Buono studio! 📚**
