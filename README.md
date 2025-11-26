# University Management System - Refactored Java Project

## 📚 Descrizione

Sistema di gestione universitaria in Java che dimostra l'applicazione pratica di:
- ✅ Principi SOLID
- ✅ Design Pattern (Repository, Template Method, Dependency Injection, Strategy)
- ✅ Interfacce e Classi Astratte
- ✅ Generics e Type Safety
- ✅ Gestione strutturata delle eccezioni
- ✅ Architettura a livelli

## 🎯 Entità Gestite

- **Student** (Studente): ID, Nome, Email
- **Professor** (Professore): ID, Nome, Dipartimento
- **Course** (Corso): ID, Nome, Crediti, ID Professore
- **Classroom** (Aula): Codice, Capacità
- **Enrollment** (Iscrizione): ID Studente, ID Corso
- **Grade** (Voto): ID Studente, ID Corso, Valore

## 🏗️ Architettura

```
┌─────────────────────────────────────┐
│  Presentation Layer (MainApp)       │
├─────────────────────────────────────┤
│  Service Layer (Services)           │
├─────────────────────────────────────┤
│  Repository Layer (Repositories)    │
├─────────────────────────────────────┤
│  Model Layer (Entities)             │
└─────────────────────────────────────┘
```

## 🚀 Come Eseguire

### Prerequisiti
- Java 17 o superiore
- Gradle 8.10 o superiore

### Comandi

```bash
# Compilare
gradle build

# Eseguire
gradle run

# Pulire e ricompilare
gradle clean build
```

## 📖 Documentazione

### Per Studiare
- **[GUIDA_STUDIO.md](GUIDA_STUDIO.md)** - Guida completa con tutti i concetti Java utilizzati, domande per l'interrogazione e spiegazioni dettagliate

### Per la Presentazione
- **[PROMPT_PRESENTAZIONE.md](PROMPT_PRESENTAZIONE.md)** - Prompt completo per generare una presentazione professionale con Gamma.app

### Documentazione Tecnica
- **[MODIFICHE_DETTAGLIATE.md](MODIFICHE_DETTAGLIATE.md)** - Documentazione dettagliata di tutte le modifiche del refactoring
- **[README_refactored.md](README_refactored.md)** - Panoramica del refactoring
- **[RIASSUNTO_PROGETTO.md](RIASSUNTO_PROGETTO.md)** - Riassunto completo del progetto

## 📊 Metriche di Miglioramento

| Aspetto | Prima | Dopo | Miglioramento |
|---------|-------|------|---------------|
| Linee repository | ~60 | ~30 | **-50%** |
| Linee service | ~70 | ~40 | **-43%** |
| Duplicazione | Alta | Minima | **-80%** |
| Testabilità | Bassa | Alta | **✓** |
| Estensibilità | Difficile | Facile | **✓** |

## 🎓 Concetti Chiave

- **SOLID Principles**: Tutti e 5 i principi applicati
- **Design Patterns**: Repository, Template Method, Dependency Injection, Strategy
- **Generics**: `Repository<I, T>`, `AbstractService<I, T>`
- **Optional**: Gestione sicura dei valori nullable
- **Custom Exceptions**: `EmptyResultException` per gestione strutturata
- **Dependency Injection**: Constructor injection per loose coupling

## 📝 Struttura del Codice

```
src/main/java/it/university/
├── MainApp.java
├── common/
│   └── EmptyResultException.java
├── model/
│   ├── Student.java
│   ├── Professor.java
│   ├── Course.java
│   ├── Classroom.java
│   ├── Enrollment.java
│   └── Grade.java
├── repository/
│   ├── Repository.java (interfaccia)
│   ├── StudentRepository.java
│   ├── ProfessorRepository.java
│   ├── CourseRepository.java
│   ├── ClassroomRepository.java
│   ├── EnrollmentRepository.java
│   ├── GradeRepository.java
│   └── impl/
│       ├── AbstractInMemoryRepository.java
│       └── EnrollmentRepositoryImpl.java
└── service/
    ├── AbstractService.java
    ├── StudentService.java
    ├── ProfessorService.java
    ├── CourseService.java
    ├── ClassroomService.java
    ├── EnrollmentService.java
    └── GradeService.java
```

## 🎯 Output Esempio

```
Studenti:
Nessuno studente presente

Studenti:
Student{1, Alice, alice@mail.com}
Student{2, Bob, bob@mail.com}

Professori:
Nessun professore presente

Professori:
Professor{1, Dr. Rossi, dept=Informatica}

Corsi:
Nessun corso presente

Corsi:
Course{1, Programmazione, 9 CFU, prof=1}
Course{2, Basi di Dati, 6 CFU, prof=null}
```

## 🏆 Punti di Forza

✅ **Manutenibile**: Codice pulito e ben organizzato  
✅ **Testabile**: Dependency injection e mock  
✅ **Estensibile**: Facile aggiungere nuove funzionalità  
✅ **Robusto**: Gestione strutturata delle eccezioni  
✅ **Professionale**: Principi SOLID e design pattern  

## 📚 Risorse per lo Studio

1. Leggi **GUIDA_STUDIO.md** per comprendere tutti i concetti
2. Usa **PROMPT_PRESENTAZIONE.md** per creare la presentazione
3. Consulta **RIASSUNTO_PROGETTO.md** per una sintesi rapida
4. Studia **MODIFICHE_DETTAGLIATE.md** per i dettagli tecnici

## 🎓 Preparazione Interrogazione

La **GUIDA_STUDIO.md** contiene:
- Spiegazione dettagliata di tutti i concetti Java
- Principi SOLID con esempi dal progetto
- Design Pattern utilizzati
- 20+ domande e risposte per l'interrogazione
- Esempi di codice commentati
- Checklist per la preparazione

## 🎤 Preparazione Presentazione

Il **PROMPT_PRESENTAZIONE.md** contiene:
- Prompt completo per Gamma.app (25 slide)
- Struttura professionale della presentazione
- Esempi di codice da mostrare
- Consigli per la presentazione
- Checklist pre-presentazione

## 📞 Supporto

Per domande o chiarimenti:
- Consulta la documentazione nella cartella del progetto
- Tutti i file .md contengono informazioni dettagliate
- Il codice è ben commentato e autodocumentante

## 🚀 Tecnologie

- **Java 17**: Linguaggio di programmazione
- **Gradle 8.10**: Build tool
- **JUnit 5**: Testing framework (configurato)
- **Design Patterns**: Repository, Template Method, DI, Strategy
- **Architecture**: Layered Architecture

---

**Progetto creato per dimostrare best practices di ingegneria del software in Java**