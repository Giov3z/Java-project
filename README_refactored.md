# Refactoring del Progetto Java Università

## Descrizione del Funzionamento dell'Applicazione (Versione Originale)

L'applicazione è una demo minimale scritta in Java per gestire entità universitarie: studenti, professori, corsi, aule, iscrizioni e voti. Utilizza un'architettura semplice con repository in-memory e service che gestiscono le operazioni CRUD di base.

### Struttura e Flusso
- **MainApp.java**: Punto d'ingresso che crea istanze dei service e chiama metodi per registrare entità e stampare liste.
- **Repository**: Classi come `StudentRepository`, `ProfessorRepository`, ecc., che memorizzano dati in strutture in-memory (Map o List).
- **Service**: Classi come `StudentService`, che creano internamente i repository e espongono metodi come `registerStudent()` e `list()`.
- **Comportamento**: Quando si chiama `list()` su un service vuoto, il service stampa un messaggio in console (es. "Nessuno studente"). Poi registra nuove entità e ristampa le liste popolate.

### Problemi della Versione Originale
- **Tight Coupling**: Service creano repository internamente → difficile testare o sostituire.
- **Side-Effects**: Stampe nei service → mescolano logica di business con presentazione.
- **Gestione Errori Implicita**: Liste vuote gestite con stampe, non con eccezioni strutturate.
- **Manutenibilità**: Codice ripetitivo, non estensibile (es. aggiungere persistenza richiede modifiche profonde).

## Refactoring del Codice

Il refactoring introduce interfacce, dependency injection e gestione errori con eccezioni custom, mantenendo il comportamento originale ma migliorando eleganza e manutenibilità.

### Principali Cambiamenti
1. **Interfaccia Generica `Repository<T>`**:
   - Definisce contratto minimo: `save(T t)` e `List<T> findAll()`.
   - Permette sostituzione implementazioni (in-memory, DB, mock per test).

2. **Eccezione Custom `EmptyResultException`**:
   - Lanciata dai service quando `findAll()` restituisce lista vuota.
   - Separazione: logica di business (service) non decide come presentare l'errore.

3. **Dependency Injection nei Service**:
   - Service ricevono repository via costruttore → loose coupling, testabilità.

4. **Duplicazione in Package `refactored`**:
   - Tutto duplicato in `it.university.refactored` per confronto diretto senza modificare originali.

### Esempi di Codice Refactorizzato

#### Interfaccia Repository
```java
public interface Repository<T> {
    void save(T t);
    List<T> findAll();
}
```

#### Service Refactorizzato (StudentService)
```java
public class StudentService {
    private final Repository<Student> repository;

    public StudentService(Repository<Student> repository) {
        this.repository = repository;
    }

    public void registerStudent(Student s) {
        repository.save(s);
    }

    public List<Student> list() {
        List<Student> all = repository.findAll();
        if (all.isEmpty()) throw new EmptyResultException("Nessuno studente presente");
        return all;
    }
}
```

#### Main Refactorizzato (MainAppRefactored)
```java
public class MainAppRefactored {
    public static void main(String[] args) {
        StudentRepository studentRepo = new StudentRepository();
        StudentService studentService = new StudentService(studentRepo);

        try {
            System.out.println("Studenti:");
            studentService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        studentService.registerStudent(new Student(1, "Alice", "alice@mail.com"));
        // ... resto simile, con try/catch per ogni lista
    }
}
```

## Gestione delle "Empty Response"

- **Prima**: Service stampavano messaggi direttamente.
- **Dopo**: Service lanciano `EmptyResultException` con messaggio specifico (es. "Nessuno studente presente").
- **Nel Main**: Cattura con `try/catch` e decide presentazione (stampa, log, UI).

Questo centralizza la gestione errori e permette flessibilità (es. in un'app web, mostrare popup invece di stampe).

## Descrizione della Soluzione Finale

La soluzione finale mantiene il comportamento originale ma è più elegante e manutenibile:
- **Architettura Pulita**: Interfacce per contratti, injection per dipendenze.
- **Testabilità**: Facile mockare repository per unit test.
- **Estensibilità**: Aggiungere persistenza (es. JPA) implementando `Repository<T>`.
- **Separazione Responsabilità**: Service per logica, Main per presentazione.
- **Confronto**: Package `refactored` permette side-by-side comparison.

### Come Eseguire
1. Clona il repo: `git clone https://github.com/Giov3z/Java-project.git`
2. Build: `gradle build`
3. Run Originale: `gradle run` (se configurato) o `java -cp build/classes/java/main it.university.MainApp`
4. Run Refactorizzato: `java -cp build/classes/java/main it.university.refactored.MainAppRefactored`

### File Chiave
- Originali: `src/main/java/it/university/MainApp.java`, `repository/`, `service/`
- Refactorizzati: `src/main/java/it/university/refactored/`

Questo refactoring dimostra principi SOLID, dependency injection e gestione errori strutturata, rendendo il codice pronto per progetti reali.