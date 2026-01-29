document.addEventListener('DOMContentLoaded', async function () {
    // Variáveis de estado da aplicação
    let nav = 0;
    let clicked = null;
    let userSubjects = [];
    let events = [];

    // Referências a elementos do DOM
    const calendar = document.getElementById('calendar');
    const monthDisplay = document.getElementById('monthDisplay');
    const newEventModal = document.getElementById('newEventModal');
    const eventIdInput = document.getElementById('eventIdInput');
    const eventTitleSelect = document.getElementById('eventTitleSelect');
    const eventNameInput = document.getElementById('eventNameInput');
    const eventLocationInput = document.getElementById('eventLocationInput');
    const eventDateInput = document.getElementById('eventDateInput');
    const eventStartTimeInput = document.getElementById('eventStartTimeInput');
    const eventDurationInput = document.getElementById('eventDurationInput');
    const deleteButton = document.getElementById('deleteButton');
    const sideMenu = document.getElementById('sideMenu');
    const menuOverlay = document.getElementById('menuOverlay');
    const subjectList = document.getElementById('subjectList');
    const addSubjectForm = document.getElementById('addSubjectForm');
    const subjectCodeInput = document.getElementById('subjectCodeInput');
    const subjectMessage = document.getElementById('subjectMessage');
    const weekdays = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];

    // --- FUNÇÕES DE LÓGICA DE EVENTOS ---

    async function loadEventsFromServer() {
        try {
            const response = await fetch('/api/events');
            if (!response.ok) {
                console.error('Falha ao carregar eventos do servidor.');
                events = [];
                return;
            }
            const serverEvents = await response.json();
            events = serverEvents.map(evt => {
                const eventDate = new Date(evt.dataHoraInicio);
                return {
                    id: evt.id,
                    nome: evt.nome,
                    date: eventDate.toISOString().slice(0, 10),
                    startTime: eventDate.toTimeString().slice(0, 5),
                    fullEvent: evt
                };
            });
        } catch (error) {
            console.error('Erro de rede ao carregar eventos:', error);
            events = [];
        }
    }

    async function saveEvent(e) {
        e.preventDefault();
        const payload = {
            id: eventIdInput.value || null,
            tipo: eventTitleSelect.value,
            nome: eventNameInput.value,
            local: eventLocationInput.value,
            data: eventDateInput.value,
            horaInicio: eventStartTimeInput.value,
            duracao: eventDurationInput.value || '0',
        };
        try {
            const response = await fetch('/api/events/save', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            if (response.ok) {
                closeModal();
            } else {
                alert('Erro ao salvar evento.');
            }
        } catch (error) {
            console.error('Erro de rede ao salvar evento:', error);
            alert('Erro de conexão ao salvar evento.');
        }
    }

    function editEvent(eventId) {
        const event = events.find(e => e.id == eventId);
        if (event) {
            const fullEvent = event.fullEvent;
            openModal(event.date);
            eventIdInput.value = fullEvent.id;
            eventNameInput.value = fullEvent.nome;
            eventLocationInput.value = fullEvent.local || '';
            eventDateInput.value = event.date;
            eventStartTimeInput.value = event.startTime;
            eventDurationInput.value = fullEvent.duracao || '';
            deleteButton.classList.remove('hidden');
        }
    }

    // --- FUNÇÕES DE LÓGICA DE MATÉRIAS ---

    async function loadUserSubjects() {
        try {
            const resUser = await fetch("/api/getUserSubjects");
            if (resUser.ok) {
                userSubjects = await resUser.json();
            } else {
                userSubjects = [];
            }
        } catch (error) {
            console.error("Erro ao carregar matérias do usuário:", error);
            userSubjects = [];
        }
        renderSubjects();
    }

    function renderSubjects() {
        subjectList.innerHTML = '';
        if (userSubjects.length === 0) {
            subjectList.innerHTML = `<p class="text-center text-gray-500">Nenhuma matéria adicionada.</p>`;
            return;
        }
        userSubjects.forEach(subject => {
            const faltas = typeof subject.faltas === 'number' ? subject.faltas : 0;
            const limiteFaltas = typeof subject.limiteFaltas === 'number' ? subject.limiteFaltas : 0;
            const absencePercentage = limiteFaltas > 0 ? Math.min((faltas / limiteFaltas),1) * 100 : 0;
            let progressBarColor = 'bg-green-500';
            if (absencePercentage > 50) progressBarColor = 'bg-yellow-500';
            if (absencePercentage >= 80) progressBarColor = 'bg-red-500';
            const card = document.createElement('div');
            card.className = 'subject-card relative bg-gray-50 p-4 rounded-lg shadow';
            card.innerHTML = `
                <button data-id="${subject.codigo}" class="remove-subject-btn absolute top-2 right-2 text-gray-400 hover:text-red-500"><i class="fas fa-times-circle"></i></button>
                <h3 class="font-bold text-gray-800 pr-4">${subject.nome}</h3>
                <p class="text-sm text-gray-600 mb-2">${subject.professor}</p>
                <div class="flex justify-between items-center text-sm mb-1"><span>Faltas</span><span>${faltas} / ${limiteFaltas}</span></div>
                <div class="w-full bg-gray-200 rounded-full h-2.5"><div class="${progressBarColor} h-2.5 rounded-full" style="width: ${absencePercentage}%"></div></div>
                <div class="flex justify-end mt-3 space-x-2">
                    <button data-id="${subject.codigo}" data-action="subtract" class="absence-btn w-8 h-8 bg-red-500 text-white rounded-full btn-hover">-</button>
                    <button data-id="${subject.codigo}" data-action="add" class="absence-btn w-8 h-8 bg-green-500 text-white rounded-full btn-hover">+</button>
                </div>`;
            subjectList.appendChild(card);
        });
    }
    
    async function handleAddSubject(e) {
        e.preventDefault();
        const code = subjectCodeInput.value.toUpperCase().trim();
        if (!code) { showSubjectMessage("Por favor, insira um código de matéria.", true); return; }
        if (userSubjects.find(s => s.codigo === code)) { showSubjectMessage('Matéria já adicionada!', true); return; }
        try {
            const response = await fetch(`/api/adicionarMateriaUsuario?codigo=${code}`, { method: 'POST' });
            if (response.ok) {
                const addedSubject = await response.json();
                userSubjects.push(addedSubject);
                renderSubjects();
                subjectCodeInput.value = '';
                showSubjectMessage('Matéria adicionada com sucesso!');
            } else {
                showSubjectMessage(response.status === 404 ? 'Código de matéria não encontrado.' : 'Erro ao adicionar matéria.', true);
            }
        } catch (error) {
            showSubjectMessage('Erro de rede. Tente novamente.', true);
        }
    }

    async function handleSubjectListClick(e) {
        const absenceBtn = e.target.closest('.absence-btn');
        const removeBtn = e.target.closest('.remove-subject-btn');
        if (absenceBtn) {
            const codigo = absenceBtn.dataset.id;
            const action = absenceBtn.dataset.action;
            try {
                const response = await fetch(`/api/atualizarFaltas?codigo=${codigo}&action=${action}`, { method: 'POST' });
                if (response.ok) {
                    // MUDANÇA AQUI: A resposta agora é mais simples
                    const updatedFaltas = await response.json();
                    const subjectIndex = userSubjects.findIndex(s => s.codigo === codigo);
                    if (subjectIndex !== -1) {
                        // Atualiza apenas o campo de faltas no objeto local
                        userSubjects[subjectIndex].faltas = updatedFaltas.faltas;
                        renderSubjects();
                    }
                }
            } catch (error) { console.error(error); }
        } else if (removeBtn) {
            const codigo = removeBtn.dataset.id;
            try {
                const response = await fetch(`/api/removerMateriaUsuario?codigo=${codigo}`, { method: 'POST' });
                if (response.ok) {
                    userSubjects = userSubjects.filter(s => s.codigo !== codigo);
                    renderSubjects();
                    showSubjectMessage('Matéria removida com sucesso!');
                } else {
                    const errorText = await response.text();
                    showSubjectMessage(`Erro: ${errorText}`, true);
                }
            } catch (error) { showSubjectMessage('Erro de rede. Tente novamente.', true); }
        }
    }
    
    // --- FUNÇÕES UTILITÁRIAS E DE UI ---

    function loadCalendar() {
        const dt = new Date();
        if (nav !== 0) dt.setMonth(new Date().getMonth() + nav);
        const day = dt.getDate();
        const month = dt.getMonth();
        const year = dt.getFullYear();
        const firstDayOfMonth = new Date(year, month, 1);
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const dateString = firstDayOfMonth.toLocaleDateString('pt-br', { weekday: 'long' });
        const paddingDays = weekdays.indexOf(dateString.charAt(0).toUpperCase() + dateString.slice(1));
        monthDisplay.innerText = `${dt.toLocaleDateString('pt-br', { month: 'long' }).toUpperCase()} ${year}`;
        calendar.innerHTML = '';
        for (let i = 1; i <= paddingDays + daysInMonth; i++) {
            const daySquare = document.createElement('div');
            daySquare.classList.add('day', 'relative', 'p-2', 'h-24', 'sm:h-32', 'bg-white', 'rounded-md', 'shadow-sm', 'cursor-pointer', 'flex', 'flex-col');
            const dayString = `${year}-${String(month + 1).padStart(2, '0')}-${String(i - paddingDays).padStart(2, '0')}`;
            if (i > paddingDays) {
                const dayOfMonth = i - paddingDays;
                daySquare.addEventListener('click', () => openModal(dayString));
                const dayNumber = document.createElement('div');
                dayNumber.innerText = dayOfMonth;
                daySquare.appendChild(dayNumber);
                const eventsForDay = events.filter(e => e.date === dayString);
                if (eventsForDay.length > 0) {
                    const eventContainer = document.createElement('div');
                    eventContainer.classList.add('mt-1', 'space-y-1', 'overflow-y-auto', 'flex-grow');
                    eventsForDay.forEach(event => {
                        const eventDiv = document.createElement('div');
                        eventDiv.classList.add('event', 'bg-blue-500', 'text-white', 'rounded', 'truncate');
                        eventDiv.innerText = event.nome;
                        eventDiv.addEventListener('click', (e) => {
                            e.stopPropagation();
                            editEvent(event.id);
                        });
                        eventContainer.appendChild(eventDiv);
                    });
                    daySquare.appendChild(eventContainer);
                }
                if (dayOfMonth === day && nav === 0) {
                    dayNumber.classList.add('bg-blue-600', 'text-white', 'w-7', 'h-7', 'rounded-full', 'flex', 'items-center', 'justify-center');
                }
            } else {
                daySquare.classList.add('bg-transparent', 'shadow-none', 'cursor-default');
            }
            calendar.appendChild(daySquare);
        }
    }

    function openModal(date) {
        clicked = date;
        eventDateInput.value = date;
        newEventModal.style.display = 'flex';
    }

    async function closeModal() {
        document.getElementById('eventForm').reset();
        eventIdInput.value = '';
        deleteButton.classList.add('hidden');
        newEventModal.style.display = 'none';
        clicked = null;
        await loadEventsFromServer();
        loadCalendar();
    }

    function showSubjectMessage(message, isError = false) {
        subjectMessage.textContent = message;
        subjectMessage.className = isError ? 'text-xs mt-1 h-4 text-red-600' : 'text-xs mt-1 h-4 text-green-600';
        setTimeout(() => subjectMessage.textContent = '', 3000);
    }
    
    function toggleSideMenu(open) {
        sideMenu.classList.toggle('menu-open', open);
        sideMenu.classList.toggle('menu-closed', !open);
        menuOverlay.style.display = open ? 'block' : 'none';
    }

    function initListeners() {
        document.getElementById('nextButton').addEventListener('click', () => { nav++; loadCalendar(); });
        document.getElementById('backButton').addEventListener('click', () => { nav--; loadCalendar(); });
        document.getElementById('addEventBtn').addEventListener('click', () => openModal(new Date().toISOString().slice(0, 10)));
        document.getElementById('cancelButton').addEventListener('click', closeModal);
        deleteButton.addEventListener('click', deleteEventFromModal);
        document.getElementById('eventForm').addEventListener('submit', saveEvent);
        document.getElementById('menuBtn').addEventListener('click', () => toggleSideMenu(true));
        document.getElementById('closeMenuBtn').addEventListener('click', () => toggleSideMenu(false));
        menuOverlay.addEventListener('click', () => toggleSideMenu(false));
        addSubjectForm.addEventListener('submit', handleAddSubject);
        subjectList.addEventListener('click', handleSubjectListClick);
    }

    async function deleteEventFromModal() {
        const eventId = eventIdInput.value;
        if (!eventId) return;

        try {
            const response = await fetch(`/api/events/delete/${eventId}`, {
                method: 'POST',
            });

            if (response.ok) {
                closeModal(); // Fecha o modal e recarrega o calendário
            } else {
                alert('Falha ao excluir o evento.');
            }
        } catch (error) {
            console.error('Erro de rede ao excluir evento:', error);
            alert('Erro de conexão ao excluir evento.');
        }
    }
    

    // --- INICIALIZAÇÃO DA PÁGINA ---
    await loadUserSubjects();
    console.log('Matérias carregadas:', userSubjects);
    userSubjects.forEach(subject => {
        console.log(`Matéria: ${subject.nome}, Código: ${subject.codigo}, Faltas: ${subject.faltas}/${subject.limiteFaltas}`);
    });
    await loadEventsFromServer();
    initListeners();
    loadCalendar();
});