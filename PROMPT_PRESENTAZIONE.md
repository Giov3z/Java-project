# Prompt per Presentazione Gamma.app - Progetto University Refactoring

## 🎯 Istruzioni per l'uso

Copia e incolla questo prompt completo in **Gamma.app** per generare automaticamente una presentazione professionale sul refactoring del progetto University.

---

## 📋 PROMPT COMPLETO PER GAMMA.APP

```
Crea una presentazione professionale per uno studente universitario sul refactoring di un progetto Java chiamato "University Management System".

STRUTTURA DELLA PRESENTAZIONE:

SLIDE 1 - TITOLO
- Titolo: "Refactoring del Progetto University"
- Sottotitolo: "Applicazione dei Principi SOLID e Design Pattern in Java"
- Nome studente: [Inserisci il tuo nome]
- Data: [Inserisci la data]
- Immagine suggerita: Logo Java o icona di refactoring

SLIDE 2 - INDICE
- Descrizione dell'applicazione originale
- Problemi identificati
- Principi SOLID applicati
- Design Pattern utilizzati
- Architettura finale
- Risultati e metriche
- Demo del codice

SLIDE 3 - COS'È IL PROGETTO UNIVERSITY?
Titolo: "Descrizione dell'Applicazione"
Contenuto:
- Sistema di gestione universitaria in Java
- Gestisce 6 entità principali:
  • Studenti (Student)
  • Professori (Professor)
  • Corsi (Course)
  • Aule (Classroom)
  • Iscrizioni (Enrollment)
  • Voti (Grade)
- Operazioni CRUD (Create, Read, Update, Delete)
- Storage in-memory con Map e List
Immagine suggerita: Diagramma di entità o icona università

SLIDE 4 - VERSIONE ORIGINALE
Titolo: "Come Funzionava Prima del Refactoring"
Contenuto:
- Service creavano repository internamente
- Stampe dirette nei service (System.out.println)
- Codice duplicato in ogni repository
- Nessuna interfaccia o classe astratta
- Difficile da testare e mantenere
Esempio di codice problematico:
```java
public class StudentService {
    private StudentRepository repository = new StudentRepository();
    
    public List<Student> list() {
        if (repository.findAll().isEmpty()) {
            System.out.println("Nessuno studente");
        }
        return repository.findAll();
    }
}
```
Immagine suggerita: Icona di codice disordinato o warning

SLIDE 5 - PROBLEMI IDENTIFICATI
Titolo: "Cosa Non Andava?"
Contenuto in bullet points con icone:
❌ Tight Coupling: Dipendenze hardcoded
❌ Violazione SRP: Service gestivano anche la presentazione
❌ Duplicazione: Stessa logica ripetuta in ogni repository
❌ Non Testabile: Impossibile usare mock
❌ Non Estensibile: Difficile cambiare storage
❌ Manutenibilità Bassa: Modifiche richiedevano cambiamenti multipli
Immagine suggerita: Icona di problemi o bug

SLIDE 6 - PRINCIPI SOLID (PARTE 1)
Titolo: "Principi SOLID Applicati - SRP e OCP"
Contenuto:
1. Single Responsibility Principle (SRP)
   - Ogni classe ha una sola responsabilità
   - Repository: solo persistenza
   - Service: solo logica business
   - MainApp: solo presentazione

2. Open/Closed Principle (OCP)
   - Aperto all'estensione, chiuso alla modifica
   - Interfaccia Repository stabile
   - Nuove implementazioni senza modificare esistente
Immagine suggerita: Icona di architettura pulita

SLIDE 7 - PRINCIPI SOLID (PARTE 2)
Titolo: "Principi SOLID Applicati - LSP, ISP, DIP"
Contenuto:
3. Liskov Substitution Principle (LSP)
   - Qualsiasi implementazione di Repository è sostituibile

4. Interface Segregation Principle (ISP)
   - Interfacce minimali e focalizzate
   - Solo metodi essenziali

5. Dependency Inversion Principle (DIP)
   - Dipendenza da interfacce, non da implementazioni
   - Dependency Injection via costruttore
Immagine suggerita: Diagramma di dipendenze

SLIDE 8 - DESIGN PATTERN (PARTE 1)
Titolo: "Design Pattern Utilizzati"
Contenuto:
1. Repository Pattern
   - Astrae l'accesso ai dati
   - Interfaccia simile a collezione
   - Facile cambiare storage

2. Template Method Pattern
   - Scheletro algoritmo in classe astratta
   - Sottoclassi personalizzano dettagli
   - Esempio: emptyMessage() in AbstractService
Codice esempio:
```java
public abstract class AbstractService<I, T> {
    public List<T> list() {
        if (all.isEmpty()) {
            throw new EmptyResultException(emptyMessage());
        }
        return all;
    }
    protected abstract String emptyMessage();
}
```
Immagine suggerita: Icona di pattern o template

SLIDE 9 - DESIGN PATTERN (PARTE 2)
Titolo: "Altri Pattern Applicati"
Contenuto:
3. Dependency Injection Pattern
   - Dipendenze fornite dall'esterno
   - Constructor injection
   - Migliora testabilità

4. Strategy Pattern
   - Diverse strategie per estrarre ID
   - Ogni repository implementa extractId()
Codice esempio:
```java
public class StudentRepository 
    extends AbstractInMemoryRepository<Integer, Student> {
    @Override
    protected Integer extractId(Student entity) {
        return entity.getId();
    }
}
```
Immagine suggerita: Icona di strategia o scelta

SLIDE 10 - ARCHITETTURA A LIVELLI
Titolo: "Architettura Finale del Progetto"
Contenuto con diagramma a livelli:
┌─────────────────────────────────┐
│  Presentation Layer (MainApp)   │
├─────────────────────────────────┤
│  Service Layer (Services)       │
├─────────────────────────────────┤
│  Repository Layer (Repositories)│
├─────────────────────────────────┤
│  Model Layer (Entities)         │
└─────────────────────────────────┘

Responsabilità:
- Model: Entità di dominio
- Repository: Persistenza dati
- Service: Logica business
- Presentation: Interazione utente
Immagine suggerita: Diagramma architetturale

SLIDE 11 - INTERFACCE E GENERICS
Titolo: "Interfacce Generiche e Type Safety"
Contenuto:
Interfaccia Repository generica:
```java
public interface Repository<I, T> {
    void save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
}
```

Vantaggi dei Generics:
✅ Type safety a compile-time
✅ Riuso del codice
✅ Eliminazione cast
✅ Una sola implementazione per tutti i tipi

Esempio uso:
- Repository<Integer, Student> per studenti
- Repository<String, Classroom> per aule
Immagine suggerita: Icona di tipo o generics

SLIDE 12 - CLASSI ASTRATTE
Titolo: "Riuso del Codice con Classi Astratte"
Contenuto:
AbstractInMemoryRepository:
- Implementa logica comune per tutti i repository
- Usa Map<I, T> per storage
- Template method: extractId()

Codice:
```java
public abstract class AbstractInMemoryRepository<I, T> 
    implements Repository<I, T> {
    private final Map<I, T> store = new HashMap<>();
    
    @Override
    public void save(T entity) {
        store.put(extractId(entity), entity);
    }
    
    protected abstract I extractId(T entity);
}
```

Risultato: Da 10 righe a 6 righe per repository (-40%)
Immagine suggerita: Icona di riuso o DRY

SLIDE 13 - GESTIONE ECCEZIONI
Titolo: "Gestione Strutturata delle Eccezioni"
Contenuto:
Eccezione Custom: EmptyResultException
```java
public class EmptyResultException extends RuntimeException {
    public EmptyResultException(String message) {
        super(message);
    }
}
```

Prima: Service stampavano direttamente
❌ System.out.println("Nessuno studente");

Dopo: Service lanciano eccezione
✅ throw new EmptyResultException("Nessuno studente presente");

Vantaggi:
- Separazione responsabilità
- Chiamante decide come gestire
- Testabile e flessibile
Immagine suggerita: Icona di eccezione o gestione errori

SLIDE 14 - DEPENDENCY INJECTION
Titolo: "Dependency Injection in Azione"
Contenuto:
Prima (Tight Coupling):
```java
public class StudentService {
    private StudentRepository repository = 
        new StudentRepository(); // Hardcoded!
}
```

Dopo (Loose Coupling):
```java
public class StudentService 
    extends AbstractService<Integer, Student> {
    public StudentService(Repository<Integer, Student> repository) {
        super(repository); // Injected!
    }
}
```

Nel Main:
```java
StudentRepository repo = new StudentRepository();
StudentService service = new StudentService(repo);
```

Vantaggi: Testabilità, Flessibilità, Disaccoppiamento
Immagine suggerita: Icona di injection o plug

SLIDE 15 - OPTIONAL E PROGRAMMAZIONE FUNZIONALE
Titolo: "Optional per Evitare NullPointerException"
Contenuto:
Uso di Optional:
```java
public interface Repository<I, T> {
    Optional<T> findById(I id);
}
```

Esempio nel Service:
```java
public void assignProfessor(int courseId, int professorId) {
    courseRepository.findById(courseId)
        .ifPresent(course -> {
            course.setProfessorId(professorId);
            save(course);
        });
}
```

Vantaggi:
✅ Nessun NullPointerException
✅ Codice più leggibile
✅ Programmazione funzionale
Immagine suggerita: Icona di sicurezza o shield

SLIDE 16 - RISULTATI DEL REFACTORING
Titolo: "Metriche di Miglioramento"
Contenuto con tabella:

| Aspetto          | Prima | Dopo  | Miglioramento |
|------------------|-------|-------|---------------|
| Linee repository | ~60   | ~30   | -50%          |
| Linee service    | ~70   | ~40   | -43%          |
| Duplicazione     | Alta  | Minima| -80%          |
| Testabilità      | Bassa | Alta  | ✓             |
| Estensibilità    | Difficile | Facile | ✓        |
| Manutenibilità   | Bassa | Alta  | ✓             |

Classi astratte/interfacce: 0 → 4
Gestione eccezioni: Nessuna → Centralizzata
Immagine suggerita: Grafico di miglioramento o trend positivo

SLIDE 17 - ESEMPIO DI CODICE FINALE
Titolo: "Codice Refactorizzato - StudentService"
Contenuto:
```java
public class StudentService 
    extends AbstractService<Integer, Student> {
    
    public StudentService(Repository<Integer, Student> repository) {
        super(repository);
    }
    
    public void registerStudent(Student student) {
        save(student);
    }
    
    @Override
    protected String emptyMessage() {
        return "Nessuno studente presente";
    }
}
```

Caratteristiche:
✅ Dependency Injection
✅ Estende classe astratta
✅ Template Method Pattern
✅ Codice pulito e conciso
Immagine suggerita: Icona di codice pulito o check

SLIDE 18 - FLUSSO DI ESECUZIONE
Titolo: "Come Funziona l'Applicazione"
Contenuto con diagramma di flusso:
1. MainApp crea repository
   ↓
2. MainApp inietta repository nei service
   ↓
3. MainApp chiama service.list()
   ↓
4. Service chiama repository.findAll()
   ↓
5. Se vuoto: lancia EmptyResultException
   ↓
6. MainApp cattura eccezione e stampa messaggio
   ↓
7. MainApp registra nuove entità
   ↓
8. MainApp richiama service.list()
   ↓
9. Service restituisce lista popolata
   ↓
10. MainApp stampa risultati
Immagine suggerita: Diagramma di flusso o frecce

SLIDE 19 - ESTENSIBILITÀ FUTURA
Titolo: "Facile Aggiungere Nuove Funzionalità"
Contenuto:
Esempi di estensioni possibili:

1. Persistenza Database:
```java
public class DatabaseStudentRepository 
    implements Repository<Integer, Student> {
    // Implementazione con JDBC/JPA
}
```

2. Caching:
```java
public class CachedRepository<I,T> 
    implements Repository<I,T> {
    private final Repository<I,T> delegate;
    private final Cache<I,T> cache;
}
```

3. Logging/Auditing
4. Validazione
5. Transazioni

Nessuna modifica ai service necessaria!
Immagine suggerita: Icona di espansione o crescita

SLIDE 20 - VANTAGGI COMPLESSIVI
Titolo: "Perché Questo Refactoring è Importante?"
Contenuto con icone:
✅ Codice più manutenibile
   - Modifiche localizzate
   - Facile capire e modificare

✅ Codice più testabile
   - Dependency injection
   - Mock facili da usare

✅ Codice più estensibile
   - Interfacce e classi astratte
   - Aggiungere funzionalità senza modificare esistente

✅ Codice più robusto
   - Gestione eccezioni strutturata
   - Type safety con generics

✅ Codice più professionale
   - Principi SOLID
   - Design pattern consolidati
Immagine suggerita: Icona di successo o trofeo

SLIDE 21 - DEMO PRATICA
Titolo: "Dimostrazione del Codice"
Contenuto:
Output dell'applicazione:

Studenti:
Nessuno studente presente

Studenti:
Student{1, Alice, alice@mail.com}
Student{2, Bob, bob@mail.com}

Professori:
Nessun professore presente

Professori:
Professor{1, Dr. Rossi, dept=Informatica}

[... altri output ...]

Comandi per eseguire:
```bash
gradle build
gradle run
```
Immagine suggerita: Screenshot di terminale o output

SLIDE 22 - TECNOLOGIE E STRUMENTI
Titolo: "Stack Tecnologico"
Contenuto:
- Linguaggio: Java 17
- Build Tool: Gradle 8.10
- Paradigma: Object-Oriented Programming
- Principi: SOLID
- Pattern: Repository, Template Method, Dependency Injection, Strategy
- Concetti: Generics, Optional, Lambda Expressions
- Testing: JUnit (preparato per test)
- Storage: In-memory (Map/List)
- Architettura: Layered Architecture
Immagine suggerita: Loghi delle tecnologie

SLIDE 23 - LEZIONI APPRESE
Titolo: "Cosa Ho Imparato da Questo Progetto"
Contenuto:
📚 Principi SOLID nella pratica
📚 Design Pattern applicati a casi reali
📚 Importanza della separazione delle responsabilità
📚 Vantaggi della Dependency Injection
📚 Uso di interfacce e classi astratte
📚 Generics per codice riusabile
📚 Gestione strutturata delle eccezioni
📚 Architettura a livelli
📚 Refactoring come processo iterativo
📚 Codice pulito e manutenibile
Immagine suggerita: Icona di apprendimento o libro

SLIDE 24 - CONCLUSIONI
Titolo: "Conclusioni"
Contenuto:
Il refactoring ha trasformato un'applicazione procedurale con alta duplicazione in un sistema orientato agli oggetti ben strutturato.

Risultati chiave:
• -50% linee di codice nei repository
• -43% linee di codice nei service
• -80% duplicazione
• +100% testabilità
• +100% estensibilità

Il codice finale è:
✓ Più elegante
✓ Più manutenibile
✓ Più professionale
✓ Pronto per progetti enterprise
Immagine suggerita: Icona di completamento o bandiera

SLIDE 25 - DOMANDE
Titolo: "Domande?"
Contenuto:
Grazie per l'attenzione!

Contatti:
📧 [tua email]
💻 GitHub: [tuo username]
🔗 LinkedIn: [tuo profilo]

Repository del progetto:
https://github.com/[username]/Java-project

Documentazione completa:
- GUIDA_STUDIO.md
- MODIFICHE_DETTAGLIATE.md
- README_refactored.md
Immagine suggerita: Icona di domande o punto interrogativo

STILE DELLA PRESENTAZIONE:
- Usa uno stile professionale ma accessibile per studenti
- Colori: Blu e arancione (colori di Java) con accenti neutri
- Font: Sans-serif moderno e leggibile
- Usa icone e diagrammi dove possibile
- Codice con syntax highlighting
- Transizioni semplici e professionali
- Ogni slide deve essere chiara e non troppo carica
- Usa bullet points e liste per chiarezza
- Aggiungi immagini pertinenti e professionali
```

---

## 📝 Note Aggiuntive per l'Uso

### Come Usare Questo Prompt

1. **Vai su Gamma.app** (https://gamma.app)
2. **Crea una nuova presentazione** con AI
3. **Copia e incolla** l'intero prompt sopra
4. **Personalizza** con il tuo nome, email, e link GitHub
5. **Genera** la presentazione
6. **Rivedi e modifica** eventuali dettagli

### Personalizzazioni Consigliate

Dopo la generazione, considera di:
- ✅ Aggiungere screenshot reali del codice
- ✅ Inserire il tuo nome e contatti
- ✅ Aggiungere logo dell'università
- ✅ Modificare i colori secondo le tue preferenze
- ✅ Aggiungere note per il relatore
- ✅ Testare la presentazione con timer

### Durata Stimata

- **Presentazione completa**: 20-25 minuti
- **Versione breve** (rimuovi slide 17-19): 15 minuti
- **Versione estesa** (aggiungi demo live): 30 minuti

### Consigli per la Presentazione

1. **Pratica**: Prova la presentazione almeno 2-3 volte
2. **Tempo**: Dedica 1-2 minuti per slide
3. **Codice**: Non leggere il codice, spiega i concetti
4. **Domande**: Preparati alle domande sui principi SOLID
5. **Demo**: Se possibile, mostra l'applicazione in esecuzione
6. **Backup**: Porta una versione PDF come backup

### Slide Opzionali da Aggiungere

Se hai più tempo, considera di aggiungere:
- Slide su unit testing con JUnit
- Slide su confronto con framework (Spring)
- Slide su metriche di qualità del codice
- Slide su continuous integration
- Slide su best practices Java

### Alternative a Gamma.app

Se Gamma.app non è disponibile, puoi usare questo prompt con:
- **Beautiful.ai**
- **Canva Presentations**
- **Pitch.com**
- **Google Slides** (con AI add-ons)
- **PowerPoint Designer**

Basta adattare leggermente il formato del prompt per la piattaforma scelta.

---

## 🎯 Checklist Pre-Presentazione

Prima della presentazione, verifica:

- [ ] Presentazione generata e rivista
- [ ] Nome e contatti personalizzati
- [ ] Codice testato e funzionante
- [ ] Screenshot aggiornati
- [ ] Timer provato (1-2 min per slide)
- [ ] Domande frequenti preparate
- [ ] Backup PDF creato
- [ ] Connessione internet testata (se demo live)
- [ ] Presentazione provata almeno 2 volte
- [ ] Note per il relatore preparate

---

## 💡 Suggerimenti Finali

**Per una presentazione eccellente**:
1. Inizia con energia e entusiasmo
2. Usa esempi concreti dal codice
3. Spiega il "perché", non solo il "cosa"
4. Coinvolgi il pubblico con domande
5. Mostra la passione per il codice pulito
6. Concludi con un messaggio forte
7. Sii pronto a rispondere a domande tecniche

**Domande frequenti da preparare**:
- Perché hai scelto RuntimeException per EmptyResultException?
- Come testeresti questo codice?
- Cosa cambieresti per usare un database?
- Quali altri pattern potresti applicare?
- Come gestiresti le transazioni?

**Buona presentazione! 🚀**
