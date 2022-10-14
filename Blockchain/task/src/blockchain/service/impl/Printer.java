package blockchain.service.impl;

import blockchain.model.Blockchain;
import blockchain.model.block.CryptocurrencyBlock;
import blockchain.model.block.MessengerBlock;

import java.util.List;

import static blockchain.config.Configuration.MAX_NUMBER_OF_SYMBOLS;
import static blockchain.config.Configuration.MAX_TIME_OF_BLOCK_GENERATING;
import static blockchain.config.Configuration.MIN_NUMBER_OF_SYMBOLS;
import static blockchain.config.Configuration.MIN_TIME_OF_BLOCK_GENERATING;
import static blockchain.config.Configuration.PRISE;

//  TODO: Interface Segregation

public class Printer {

    public static void printBlock(MessengerBlock block, Blockchain blockchain) {
        System.out.println("\nBlock:" +
                "\nCreated by miner # " + block.getMinerNumber() +
                "\nId: " + block.getId() +
                "\nTimestamp: " + block.getTimestamp() +
                "\nMagic number: " + block.getMagicNumber() +
                "\nHash of the previous block:\n" + block.getPreviousHash() +
                "\nHash of the block:\n" + block.getHash() +
                "\nBlock data: \n" + formatData(block.getData(), "no messages") +
                "\nBlock was generating for " + block.getTimeOfGenerating() + " seconds");
        printAndSetNumberOfSymbols(block.getTimeOfGenerating(), blockchain);
    }

    public static void printBlock(CryptocurrencyBlock block, Blockchain blockchain) {
        System.out.println("\nBlock:" +
                "\nCreated by: miner" + block.getMinerNumber() +
                "\nminer" + block.getMinerNumber() + " gets " + PRISE + " VC" +
                "\nId: " + block.getId() +
                "\nTimestamp: " + block.getTimestamp() +
                "\nMagic number: " + block.getMagicNumber() +
                "\nHash of the previous block:\n" + block.getPreviousHash() +
                "\nHash of the block:\n" + block.getHash() +
                "\nBlock data: \n" + formatData(block.getData(), "no transactions") +
                "\nBlock was generating for " + block.getTimeOfGenerating() + " seconds");
        printAndSetNumberOfSymbols(block.getTimeOfGenerating(), blockchain);
    }

    private static String formatData(List<?> data, String defaultData) {
        if (data.isEmpty()) {
            return defaultData;
        }
        StringBuilder stringBuilder = new StringBuilder();
        data.forEach(line -> stringBuilder.append(line).append("\n"));
        return stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()).toString();
    }

    private static void printAndSetNumberOfSymbols(long timeOfGenerating, Blockchain blockchain) {
        int numberOfSymbols = blockchain.getNumberOfSymbols();

        if (timeOfGenerating <= MIN_TIME_OF_BLOCK_GENERATING && numberOfSymbols < MAX_NUMBER_OF_SYMBOLS) {
            blockchain.setNumberOfSymbols(++numberOfSymbols);
            System.out.println("N was increased to " + numberOfSymbols);
        } else if (timeOfGenerating > MAX_TIME_OF_BLOCK_GENERATING && numberOfSymbols - 1 > MIN_NUMBER_OF_SYMBOLS) {
            blockchain.setNumberOfSymbols(--numberOfSymbols);
            System.out.println("N was decreased to " + numberOfSymbols);
        } else {
            System.out.println("N stays the same");
        }
    }
}
