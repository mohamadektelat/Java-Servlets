//----------------------------------------------------------------------------------------------------------------------

class resultElement {

    //------------------------------------------------------------------------------------------------------------------

    constructor(option, votesCount) {
        this.block = document.createElement('div');
        const optionText = document.createElement('h5');
        optionText.appendChild(document.createTextNode(option.option))
        const optionProgress = document.createElement('div');
        optionProgress.classList.add('progress', 'ml-auto', 'mr-auto', 'mb-2', 'w-75', 'h-10');
        const valueNow = votesCount == 0 ? 0 : (option.votes / votesCount) * 100
        const progressBar = document.createElement('div');
        progressBar.classList.add('progress-bar', 'progress-bar-striped','progress-bar-animated');
        progressBar.style.width = valueNow + "%";
        progressBar.setAttribute('role', 'progressbar');
        progressBar.setAttribute('aria-valuenow', valueNow);
        progressBar.setAttribute('aria-valuemin', 0);
        progressBar.setAttribute('aria-valuemax', 100);
        progressBar.appendChild(document.createTextNode(option.votes));
        optionProgress.appendChild(progressBar);
        this.block.appendChild(optionProgress);
    }

    //------------------------------------------------------------------------------------------------------------------

    getElement = () => this.block;
}

//----------------------------------------------------------------------------------------------------------------------

class OptionElement {

    //------------------------------------------------------------------------------------------------------------------

    constructor(option, id) {
        this.block = document.createElement('div');
        this.block.classList.add('custom-control', 'custom-radio', 'inline-block');
        const inputElement = document.createElement('input');
        inputElement.type = 'radio';
        inputElement.name = 'option';
        inputElement.id = 'option'+id;
        inputElement.value = id;
        inputElement.classList.add('custom-control-input');
        inputElement.addEventListener('change', alertBox.hide);
        const labelElement = document.createElement('label');
        labelElement.classList.add('custom-control-label');
        labelElement.setAttribute('for', 'option'+id);
        labelElement.appendChild(document.createTextNode(option));
        this.block.appendChild(inputElement);
        this.block.appendChild(labelElement);
    }

    //--------------------------------------------------------------------------------------------------------------

    getElement = () => this.block;
    block;

    //------------------------------------------------------------------------------------------------------------------

}

//----------------------------------------------------------------------------------------------------------------------

(function () {

    function hasVoted() {
        const radios = document.getElementsByName("option");
        for(let i=0 ;i<radios.length; i++)
            if(radios[i].checked)
                return true;
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------

    function processOptions(data) {
        document.getElementById('question').appendChild(document.createTextNode(data.question))
        for(let i=0; i<data.options.length; i++) {
            let element = document.getElementById('vote_form')
            if(element)
                element.appendChild(new OptionElement(data.options[i].option, i).getElement())
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    document.addEventListener('DOMContentLoaded', function () {
        let element = document.getElementsByTagName('form')[0];
        if(element) {
            element.addEventListener('submit', event => {
                if (!hasVoted()) {
                    event.preventDefault();
                    alertBox.show("You have to vote!", true)
                }
            });
        }

        Utils.dataProcessor.run(processOptions);

    }, false);

})();

//----------------------------------------------------------------------------------------------------------------------

function getVotesCount(options) {
    let count =0;
    options.forEach(answer => count+=answer.votes);
    return count;
}

//----------------------------------------------------------------------------------------------------------------------

function processResults(data) {
    const votesCount = getVotesCount(data.options)
    document.getElementById('votesCount').innerText+=votesCount
    for(let i=0; i<data.options.length; i++) {
        document.getElementById('data_block').appendChild(document.createTextNode(data.options[i].option))
        document.getElementById('data_block').appendChild(
            (new resultElement(data.options[i], votesCount)).getElement())
    }
}

//----------------------------------------------------------------------------------------------------------------------

document.addEventListener('DOMContentLoaded', function () {

    Utils.dataProcessor.run(processResults);

}, false);

//----------------------------------------------------------------------------------------------------------------------
