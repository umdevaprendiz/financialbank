async function saveUser(){
const name = Cadastro.getElementById('nome').value;
const email = Cadastro.getElementById('email').value;
const senha = Cadastro.getElementById('senha').value;
const cpf = Cadastro.getElementById('Cpf').value;

try {
        const resposta = await fetch('http://localhost:8080/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userDTO)
        });

        if (resposta.ok) {
            alert("Usuário cadastrado com sucesso!");
        } else {
            const erro = await resposta.text();
            alert("Erro ao cadastrar: " + erro);
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
    }
}
}
//criando interação da homepage...
async function homepage(){
const nome = home.getElementById('nome').value;

}